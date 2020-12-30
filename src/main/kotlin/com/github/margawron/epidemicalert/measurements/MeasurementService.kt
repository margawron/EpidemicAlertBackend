package com.github.margawron.epidemicalert.measurements

import com.github.margawron.epidemicalert.common.IdMapping
import com.github.margawron.epidemicalert.exceptions.ErrorCodeException
import com.github.margawron.epidemicalert.users.User
import com.github.margawron.epidemicalert.users.UserService
import org.springframework.stereotype.Service
import javax.transaction.Transactional

@Service
class MeasurementService(
    private val userService: UserService,
    private val measurementRepository: MeasurementRepository,
) {

    fun saveMeasurement(measurement: Measurement): Measurement =measurementRepository.save(measurement)

    @Transactional
    fun saveMeasurementsAndGetIdMappings(user: User, deviceId: Long, measurementsDto: List<MeasurementDto>): List<IdMapping> {

        val device = user.userDevices.find { it.id!! == deviceId } ?: throw ErrorCodeException(
            this::class,
            "device.device_with_id_not_exists"
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
}