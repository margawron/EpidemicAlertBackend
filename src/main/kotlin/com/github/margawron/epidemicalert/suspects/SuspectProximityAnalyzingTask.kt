package com.github.margawron.epidemicalert.suspects

import com.github.margawron.epidemicalert.alerts.AlertService
import com.github.margawron.epidemicalert.common.GeoUtils
import com.github.margawron.epidemicalert.measurements.Measurement
import com.github.margawron.epidemicalert.measurements.MeasurementService
import com.github.margawron.epidemicalert.users.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.Instant
import java.time.temporal.ChronoUnit
import kotlin.math.max

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
class SuspectProximityAnalyzingTask(
    private val alertService: AlertService,
    private val measurementService: MeasurementService,
    private val suspectService: SuspectService,
) : Runnable {

    companion object{
        val log : Logger = LoggerFactory.getLogger(SuspectProximityAnalyzingTask::class.java)
    }

    lateinit var suspect:Suspect

    @Transactional
    override fun run() {
        val pathogen = suspect.pathogen
        val startTime = suspect.startTime
        val endTime = startTime.plus(pathogen.contagiousPeriod.toLong(), pathogen.contagiousPeriodResolution.chronoUnit)
        val potentialVictims = measurementService.findUsersInSuspectMeasurementsBoundingBox(suspect, startTime, endTime)
        for(potentialVictim in potentialVictims){
            checkVictimForProximity(potentialVictim, startTime, endTime)
        }
        suspectService.saveSuspect(suspect)
    }

    private fun checkVictimForProximity(victim: User, startMoment:Instant, endMoment: Instant){
        var first = startMoment
        var second = getNextTimeIteration(startMoment, ChronoUnit.DAYS, endMoment)
        lateinit var suspectFirstMeasurement: Measurement
        lateinit var victimFirstMeasurement: Measurement
        while(second.plusMillis(1).isBefore(endMoment)){
            val suspectMeasurements = measurementService.getMeasurementsForUserBetweenInstants(suspect.suspect, first, second)
            val victimMeasurements = measurementService.getMeasurementsForUserBetweenInstants(victim, first, second)
            if(suspectMeasurements.isEmpty() || victimMeasurements.isEmpty()){
                first = second
                second = getNextTimeIteration(second, ChronoUnit.DAYS, endMoment)
                continue
            }
            if (first == startMoment){
                suspectFirstMeasurement = suspectMeasurements.removeAt(0)
                victimFirstMeasurement = victimMeasurements.removeAt(0)
            }

            checkProximityForDay(victim, suspectMeasurements, suspectFirstMeasurement, victimMeasurements, victimFirstMeasurement)

            suspectFirstMeasurement = suspectMeasurements.last()
            victimFirstMeasurement = victimMeasurements.last()
            first = second
            second = getNextTimeIteration(second, ChronoUnit.DAYS, endMoment)
        }
    }

    fun checkProximityForDay(
        victim: User,
        suspectMeasurements: List<Measurement>,
        suspectLastMeasurement: Measurement,
        victimMeasurements: List<Measurement>,
        victimLastMeasurement: Measurement
    ) {
        val victimSetOfProximities = mutableSetOf<Measurement>()
        val suspectSetOfProximities = mutableSetOf<Measurement>()
        var lastSuspectMeasurement = suspectLastMeasurement
        for(suspectMeasurement in suspectMeasurements){
            var lastVictimMeasurement = victimLastMeasurement
            for (victimMeasurement in victimMeasurements){
                val victimInterpolationIterator = MeasurementInterpolationIterator(lastVictimMeasurement, victimMeasurement)
                val victimAccuracy = max(lastVictimMeasurement.accuracy, victimMeasurement.accuracy)
                val suspectInterpolationIterator = MeasurementInterpolationIterator(lastSuspectMeasurement, suspectMeasurement)
                val suspectAccuracy = max(lastSuspectMeasurement.accuracy, suspectMeasurement.accuracy)
                if(isInProximity(suspectInterpolationIterator, victimInterpolationIterator, suspectAccuracy, victimAccuracy)){
                    victimSetOfProximities.add(lastVictimMeasurement)
                    victimSetOfProximities.add(victimMeasurement)
                    suspectSetOfProximities.add(lastSuspectMeasurement)
                    suspectSetOfProximities.add(suspectMeasurement)
                }
                lastVictimMeasurement = victimMeasurement
            }
            lastSuspectMeasurement = suspectMeasurement
        }
        if(victimSetOfProximities.isNotEmpty()) {
            val alert = alertService.createAlert(victim, suspect, victimSetOfProximities, suspectSetOfProximities)
            suspect.alerts.add(alert)
        }
    }

    private fun isInProximity(
        suspectLatLngInterpolations: MeasurementInterpolationIterator,
        victimLatLngInterpolations: MeasurementInterpolationIterator,
        suspectAccuracy: Float,
        victimAccuracy: Float
    ): Boolean {
        for (suspectLatLng in suspectLatLngInterpolations) {
            for (victimLatLng in victimLatLngInterpolations) {
                val distanceBetweenLerps = GeoUtils.getMetersDistanceBetween(suspectLatLng, victimLatLng)
                if (distanceBetweenLerps < 50.0) {
                    log.info("Got: $distanceBetweenLerps for suspect: ${suspectLatLng.latitude} ${suspectLatLng.longitude} victim: ${victimLatLng.latitude} ${victimLatLng.longitude}")
                }
                return (distanceBetweenLerps <= suspectAccuracy + victimAccuracy + suspect.pathogen.accuracy + suspect.pathogen.detectionRange)
            }
        }
        return false
    }

    fun getNextTimeIteration(start: Instant, resolution: ChronoUnit, upperMargin: Instant): Instant{
        val nextIter = start.plus(1, resolution)
        return if(nextIter.plusMillis(1).isAfter(upperMargin)) upperMargin else nextIter
    }
}