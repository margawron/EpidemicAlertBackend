package com.github.margawron.epidemicalert.location

import java.time.Instant

data class LocationDto (
    var id: Long? = null,
    var expiryDate: Instant,
    var latitude: Double,
    var longitude: Double,
    var locationType: LocationType,
    var description: String
){
    companion object{
        fun fromEntity(location: Location) = LocationDto(
            location.id,
            location.expiryDate,
            location.latitude,
            location.longitude,
            location.locationType,
            location.description
        )
    }
}