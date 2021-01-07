package com.github.margawron.epidemicalert.suspects

import com.github.margawron.epidemicalert.alerts.AlertService
import com.github.margawron.epidemicalert.common.GeoUtils
import com.github.margawron.epidemicalert.measurements.Measurement
import com.github.margawron.epidemicalert.measurements.MeasurementService
import com.github.margawron.epidemicalert.users.User
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.stereotype.Component
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

    lateinit var suspect:Suspect

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
        lateinit var suspectLastMeasurement: Measurement
        lateinit var victimLastMeasurement: Measurement
        while(second.plusMillis(1).isBefore(endMoment)){
            val suspectMeasurements = measurementService.getMeasurementsForUserBetweenInstants(suspect.suspect, first, second)
            val victimMeasurements = measurementService.getMeasurementsForUserBetweenInstants(victim, first, second)
            if (first == startMoment){
                suspectLastMeasurement = suspectMeasurements.removeAt(0)
                victimLastMeasurement = victimMeasurements.removeAt(0)
            }

            checkProximityForDay(victim, suspectMeasurements, suspectLastMeasurement, victimMeasurements, victimLastMeasurement)

            suspectLastMeasurement = suspectMeasurements.last()
            victimLastMeasurement = victimMeasurements.last()
            first = second
            second = getNextTimeIteration(second, ChronoUnit.DAYS, endMoment)
        }
    }

    fun checkProximityForDay(victim: User, suspectMeasurements: List<Measurement>, suspectLastMeasurement: Measurement, victimMeasurements: List<Measurement>, victimLastMeasurement: Measurement){
        val victimSetOfProximities = mutableSetOf<Measurement>()
        val suspectSetOfProximities = mutableSetOf<Measurement>()
        var lastSuspectMeasurement = suspectLastMeasurement
        for(suspectMeasurement in suspectMeasurements){
            val suspectTimeDiff = Duration.between(lastSuspectMeasurement.timestamp, suspectMeasurement.timestamp)
            val suspectDistance = GeoUtils.getMetersDistanceBetween(lastSuspectMeasurement.toLatLng(), suspectMeasurement.toLatLng())
            val suspectBearing = GeoUtils.getBearingBetween(lastSuspectMeasurement.toLatLng(), suspectLastMeasurement.toLatLng())
            val x = 30
            val suspectMetersPerXSeconds = (suspectDistance/suspectTimeDiff.seconds)*x
            val suspectIterations = suspectTimeDiff.seconds % x
            val suspectAccuracy = max(lastSuspectMeasurement.accuracy, suspectMeasurement.accuracy)
            for(i in 0..suspectIterations){
                val suspectIterationDistance = suspectMetersPerXSeconds*i
                val lerpedSuspectLatLng = GeoUtils.getPointInDirectionToBearing(lastSuspectMeasurement.toLatLng(), suspectBearing, suspectIterationDistance)
                var lastVictimMeasurement = victimLastMeasurement
                for (victimMeasurement in victimMeasurements){
                    val victimTimeDiff = Duration.between(lastVictimMeasurement.timestamp, victimMeasurement.timestamp)
                    val victimDistance = GeoUtils.getMetersDistanceBetween(lastVictimMeasurement.toLatLng(), victimMeasurement.toLatLng())
                    val victimBearing = GeoUtils.getBearingBetween(lastVictimMeasurement.toLatLng(), victimMeasurement.toLatLng())
                    val victimMetersPerXSeconds = (victimDistance/victimTimeDiff.seconds)*x
                    val victimIterations = victimTimeDiff.seconds % x
                    val victimAccuracy = max(lastSuspectMeasurement.accuracy, suspectMeasurement.accuracy)
                    for(j in 0..victimIterations){
                        val victimIterationDistance = victimMetersPerXSeconds*j
                        val lerpedVictimLatLng = GeoUtils.getPointInDirectionToBearing(lastVictimMeasurement.toLatLng(), victimBearing, victimIterationDistance)
                        val distanceBetweenLerps = GeoUtils.getMetersDistanceBetween(lerpedSuspectLatLng, lerpedVictimLatLng)
                        if(distanceBetweenLerps <= suspectAccuracy + victimAccuracy + suspect.pathogen.accuracy + suspect.pathogen.detectionRange){
                            victimSetOfProximities.add(lastVictimMeasurement)
                            victimSetOfProximities.add(victimMeasurement)
                            suspectSetOfProximities.add(lastSuspectMeasurement)
                            suspectSetOfProximities.add(suspectMeasurement)
                        }
                    }
                    lastVictimMeasurement = victimMeasurement
                }
            }

            lastSuspectMeasurement = suspectMeasurement
        }
        if(victimSetOfProximities.isNotEmpty()) {
            val alert = alertService.createAlert(victim, suspect, victimSetOfProximities, suspectSetOfProximities)
            suspect.alerts.add(alert)
        }
    }

    fun getNextTimeIteration(start: Instant, resolution: ChronoUnit, upperMargin: Instant): Instant{
        val nextIter = start.plus(1, resolution)
        return if(nextIter.plusMillis(1).isAfter(upperMargin)) upperMargin else nextIter
    }
}