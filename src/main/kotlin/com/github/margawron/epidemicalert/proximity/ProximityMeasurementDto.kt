package com.github.margawron.epidemicalert.proximity

import java.time.Instant

data class ProximityMeasurementDto(
    var id: Long,
    var timestamp: Instant,
    var latitude: Double,
    var longitude: Double,
){
    companion object{
        fun fromMeasurement(proximityMeasurement: ProximityMeasurement) = ProximityMeasurementDto(
            proximityMeasurement.measurement.id!!,
            proximityMeasurement.measurement.timestamp,
            proximityMeasurement.measurement.latitude,
            proximityMeasurement.measurement.longitude
        )
    }
}