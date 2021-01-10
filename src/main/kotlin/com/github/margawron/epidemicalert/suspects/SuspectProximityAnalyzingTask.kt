package com.github.margawron.epidemicalert.suspects

import com.github.margawron.epidemicalert.alerts.AlertService
import com.github.margawron.epidemicalert.common.GeoUtils
import com.github.margawron.epidemicalert.common.LatLng
import com.github.margawron.epidemicalert.common.PageableIterator
import com.github.margawron.epidemicalert.measurements.Measurement
import com.github.margawron.epidemicalert.measurements.MeasurementService
import com.github.margawron.epidemicalert.pathogens.Pathogen
import com.github.margawron.epidemicalert.users.User
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.config.ConfigurableBeanFactory
import org.springframework.context.annotation.Scope
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import java.time.Duration
import java.time.Instant
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter
import kotlin.math.max

@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
@Component
class SuspectProximityAnalyzingTask(
    private val alertService: AlertService,
    private val measurementService: MeasurementService,
    private val suspectService: SuspectService,
) : Runnable {

    companion object {
        val log: Logger = LoggerFactory.getLogger(SuspectProximityAnalyzingTask::class.java)
    }

    lateinit var suspect: Suspect
    lateinit var suspicionStartTime: Instant
    lateinit var suspicionEndTime: Instant

    @Transactional
    override fun run() {
        val pathogen = suspect.pathogen
        suspicionStartTime = suspect.startTime
        suspicionEndTime = suspicionStartTime.plus(pathogen.contagiousPeriod.toLong(), pathogen.contagiousPeriodResolution.chronoUnit)
        val potentialVictims = measurementService.findUsersInSuspectMeasurementsBoundingBox(suspect, suspicionStartTime, suspicionEndTime)
        for (potentialVictim in potentialVictims) {
            prepareData(potentialVictim)
        }
        suspectService.saveSuspect(suspect)
    }

    private fun prepareData(victim: User) {
        val pageRequest = PageRequest.of(0, 500)
        val suspectMeasurementsIterator = PageableIterator(pageRequest) {
            measurementService.getMeasurementsForUserBetweenInstants(suspect.suspect, suspicionStartTime, suspicionEndTime, it)
        }
        val victimMeasurementsIterator = PageableIterator(pageRequest) {
            measurementService.getMeasurementsForUserBetweenInstants(victim, suspicionStartTime, suspicionEndTime, it)
        }
        log.info("Got ${suspectMeasurementsIterator.getTotalElements()} elements for suspect and ${victimMeasurementsIterator.getTotalElements()} for victim")
        // Should be at least one for each one
        var suspectTailingMeasurement = suspectMeasurementsIterator.next()
        var victimTailingMeasurement = victimMeasurementsIterator.next()
        // Nothing really to measure
        if(suspectMeasurementsIterator.getTotalElements() <= 2L || victimMeasurementsIterator.getTotalElements() <= 2L){
            log.info("Too little elements to start")
            return
        }
        lateinit var startInstant: Instant
        if (victimTailingMeasurement.timestamp.isAfter(suspectTailingMeasurement.timestamp)) {
            // Advance suspect
            val tailingMeasurement =
                advanceIteratorAfterTimestampAndReturnTailing(suspectMeasurementsIterator, victimTailingMeasurement)
                    ?: return
            suspectTailingMeasurement = tailingMeasurement
            startInstant = victimTailingMeasurement.timestamp
        } else {
            // Advance victim
            val tailingMeasurement =
                advanceIteratorAfterTimestampAndReturnTailing(victimMeasurementsIterator, suspectTailingMeasurement)
                    ?: return
            victimTailingMeasurement = tailingMeasurement
            startInstant = suspectTailingMeasurement.timestamp
        }
        log.info("Starting at: ${startInstant.atOffset(ZoneOffset.UTC).format(DateTimeFormatter.ISO_DATE_TIME)} time point")
        log.info("First victim measurement is: ${victimTailingMeasurement.latitude} ${victimTailingMeasurement.longitude}")
        log.info("First suspect measurement is: ${suspectTailingMeasurement.latitude} ${suspectTailingMeasurement.longitude}")
        interpolateMeasurements(victim, startInstant, suspectTailingMeasurement, suspectMeasurementsIterator, victimTailingMeasurement, victimMeasurementsIterator)
    }

    private fun advanceIteratorAfterTimestampAndReturnTailing(iterator: PageableIterator<Measurement>, advanceJustAfter: Measurement): Measurement?{
        while(iterator.hasNext()){
            val tailingMeasurement = iterator.next()
            if (advanceJustAfter.timestamp.isBefore(tailingMeasurement.timestamp)){
                return tailingMeasurement
            }
        }
        return null
    }

    private fun interpolateMeasurements(
        victim: User,
        timestampImpositionStartInstant: Instant,
        suspectLastMeasurement: Measurement,
        suspectMeasurementsIterator: PageableIterator<Measurement>,
        victimLastMeasurement: Measurement,
        victimMeasurementsIterator: PageableIterator<Measurement>
    ) {
        var firstSuspectMeasurement = suspectLastMeasurement
        var secondSuspectMeasurement = suspectMeasurementsIterator.next()
        var firstVictimMeasurement = victimLastMeasurement
        var secondVictimMeasurement = victimMeasurementsIterator.next()

        var measuredInstant = timestampImpositionStartInstant
        var startOfDay = measuredInstant.atOffset(ZoneOffset.UTC)
            .toLocalDate().atStartOfDay()
        var endOfDay = startOfDay.plusDays(1)

        var suspectAlertMeasurements: MutableSet<Measurement> = mutableSetOf()
        var victimAlertMeasurements: MutableSet<Measurement> = mutableSetOf()
        val pathogen: Pathogen = suspect.pathogen

        var insertFollowingSuspectMeasurement = false
        var insertFollowingVictimMeasurement = false

        // Main interpolation loop
        while(measuredInstant.isBefore(suspicionEndTime)){
            // Change daily bounds/send alert
            if(measuredInstant.isAfter(endOfDay.toInstant(ZoneOffset.UTC))){
                createAlertForVictim(victim, victimAlertMeasurements, suspectAlertMeasurements)
                endOfDay = startOfDay
                startOfDay = startOfDay.plusDays(1)
                suspectAlertMeasurements = mutableSetOf()
                victimAlertMeasurements = mutableSetOf()
            }
            // Measurements are old, fetch next
            if(measuredInstant.isAfter(secondSuspectMeasurement.timestamp) && suspectMeasurementsIterator.hasNext()){
                firstSuspectMeasurement = secondSuspectMeasurement
                secondSuspectMeasurement = suspectMeasurementsIterator.next()
                if(insertFollowingSuspectMeasurement){
                    suspectAlertMeasurements.add(secondSuspectMeasurement)
                    insertFollowingSuspectMeasurement = false
                }
            } else if(!suspectMeasurementsIterator.hasNext()){
                // No new measurements
                break
            }
            // Measurements are old, fetch next
            if(measuredInstant.isAfter(secondVictimMeasurement.timestamp) && victimMeasurementsIterator.hasNext()){
                firstVictimMeasurement = secondVictimMeasurement
                secondVictimMeasurement = victimMeasurementsIterator.next()
                if(insertFollowingVictimMeasurement){
                    victimAlertMeasurements.add(secondSuspectMeasurement)
                    insertFollowingVictimMeasurement = false
                }
            }else if(!victimMeasurementsIterator.hasNext()) {
                // No new measurements
                break
            }
            val suspectLatLng = getLatLngForInstantBetweenLocations(firstSuspectMeasurement, secondSuspectMeasurement, measuredInstant)
            val suspectAccuracy = max(firstSuspectMeasurement.accuracy, secondSuspectMeasurement.accuracy)
            val victimLatLng = getLatLngForInstantBetweenLocations(firstVictimMeasurement, secondVictimMeasurement, measuredInstant)
            val victimAccuracy = max(firstVictimMeasurement.accuracy, secondVictimMeasurement.accuracy)

            val maximalError = suspectAccuracy + victimAccuracy + pathogen.detectionRange + pathogen.detectionRange
            val distanceBetweenInterpolations = GeoUtils.getMetersDistanceBetween(suspectLatLng, victimLatLng)
            if(distanceBetweenInterpolations <= maximalError){
                suspectAlertMeasurements.add(firstSuspectMeasurement)
                suspectAlertMeasurements.add(secondSuspectMeasurement)
                victimAlertMeasurements.add(firstVictimMeasurement)
                victimAlertMeasurements.add(secondVictimMeasurement)
                insertFollowingSuspectMeasurement = true
                insertFollowingVictimMeasurement = true
            }
            // Increase time interval
            measuredInstant = measuredInstant.plusSeconds(2)
        }
        if(suspectAlertMeasurements.isNotEmpty() || victimAlertMeasurements.isNotEmpty()){
            createAlertForVictim(victim, victimAlertMeasurements, suspectAlertMeasurements)
        }
    }

    private fun getLatLngForInstantBetweenLocations(
        first: Measurement,
        second: Measurement,
        measuredInstant: Instant
    ): LatLng {
        val firstLatLng = first.toLatLng()
        val secondLatLng = second.toLatLng()
        val bearing = GeoUtils.getBearingBetween(firstLatLng, secondLatLng)
        val distance = GeoUtils.getMetersDistanceBetween(firstLatLng, secondLatLng)

        val duration = Duration.between(first.timestamp, measuredInstant)
        val distanceFromFirst = distance / duration.seconds
        return GeoUtils.getPointInDirectionToBearing(firstLatLng, bearing, distanceFromFirst)
    }

    private fun createAlertForVictim(
        victim: User,
        victimAlertMeasurements: MutableSet<Measurement>,
        suspectAlertMeasurements: MutableSet<Measurement>
    ) {
        alertService.createAlert(victim, suspect, victimAlertMeasurements, suspectAlertMeasurements)
    }

}