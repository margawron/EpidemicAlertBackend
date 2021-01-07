package com.github.margawron.epidemicalert.measurements

import com.github.margawron.epidemicalert.common.IdMapping
import com.github.margawron.epidemicalert.exceptions.ErrorCodeException
import com.github.margawron.epidemicalert.suspects.Suspect
import com.github.margawron.epidemicalert.users.User
import com.github.margawron.epidemicalert.users.UserService
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import javax.transaction.Transactional

@Service
class MeasurementService(
    private val userService: UserService,
    private val measurementRepository: MeasurementRepository,
) {

    fun saveMeasurement(measurement: Measurement): Measurement = measurementRepository.save(measurement)

    @Transactional
    fun saveMeasurementsAndGetIdMappings(user: User, deviceId: Long, measurementsDto: List<MeasurementDto>): List<IdMapping> {

        val device = user.userDevices.find { it.id!! == deviceId } ?: throw ErrorCodeException(
            this::class,
            "device.given_id_does_not_exist"
        )
        return measurementsDto.map{
            val measurement = Measurement(
                timestamp = it.measurementTime,
                latitude = it.latitude,
                longitude = it.longitude,
                accuracy = it.accuracy,
                bearing = it.bearing,
                bearingAccuracy = it.bearingAccuracy,
                originOfMeasurement = device,
                ownerOfMeasurement = user
            )
            val savedMeasurement = saveMeasurement(measurement)
            return@map IdMapping(
                incomingId = it.clientSideId,
                outgoingId = savedMeasurement.id!!
            )
            // TODO validate if user had contact with infected
        }
    }

    fun findUsersInSuspectMeasurementsBoundingBox(suspect: Suspect, startMoment: Instant, endMoment: Instant): Set<User> {
        val setOfUsers = mutableSetOf<User>()
        var first = startMoment
        var second = getNextTimeIteration(startMoment, ChronoUnit.DAYS, endMoment)
        while(second.plusMillis(1).isBefore(endMoment)){
            val usersWithMeasurementsInbounds = userDiscoveryIteration(suspect, first, second)

            first = second
            second = getNextTimeIteration(second, ChronoUnit.DAYS, endMoment)

            setOfUsers.addAll(usersWithMeasurementsInbounds)
        }

        val usersWithMeasurementsInbounds = userDiscoveryIteration(suspect, first, second)
        setOfUsers.addAll(usersWithMeasurementsInbounds)

        return setOfUsers
    }

    private fun userDiscoveryIteration(
        suspect: Suspect,
        first: Instant,
        second: Instant
    ): List<User> {
        val iterationTopRightMeasurement =
            measurementRepository.findFirstByOwnerOfMeasurementAndTimestampAfterAndTimestampBeforeOrderByLongitudeDescLatitudeDesc(
                suspect.suspect,
                first,
                second
            )
        val iterationBottomLeftMeasurement =
            measurementRepository.findFirstByOwnerOfMeasurementAndTimestampAfterAndTimestampBeforeOrderByLongitudeAscLatitudeAsc(
                suspect.suspect,
                first,
                second
            )
        return if (iterationTopRightMeasurement == null || iterationBottomLeftMeasurement == null) emptyList()
        else measurementRepository.findAllUsersInbounds(
            iterationTopRightMeasurement.latitude,
            iterationTopRightMeasurement.longitude,
            iterationBottomLeftMeasurement.latitude,
            iterationBottomLeftMeasurement.longitude
        )
    }

    fun getNextTimeIteration(start: Instant, resolution: ChronoUnit, upperMargin: Instant): Instant{
        val nextIter = start.plus(1, resolution)
        return if(nextIter.plusMillis(1).isAfter(upperMargin)) upperMargin else nextIter
    }

    fun getMeasurementsForUserBetweenInstants(user: User, startInstant: Instant, endInstant: Instant): MutableList<Measurement> {
        return measurementRepository.findAllByOwnerOfMeasurementAndTimestampAfterAndTimestampBeforeOrderByTimestampAsc(user, startInstant, endInstant)
    }
}