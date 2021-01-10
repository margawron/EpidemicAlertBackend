package com.github.margawron.epidemicalert.location

import com.github.margawron.epidemicalert.common.GeoUtils
import org.springframework.stereotype.Service
import java.time.Instant


@Service
class LocationService(
    private val locationRepository: LocationRepository
) {
    fun getLocationsFromLatLngInDistanceOf(lat: Double, lng: Double, meters: Double): List<Location> {
        // https://www.rapidtables.com/convert/number/degrees-to-degrees-minutes-seconds.html
        // http://www.movable-type.co.uk/scripts/latlong.html
        // https://stackoverflow.com/questions/7477003/calculating-new-longitude-latitude-from-old-n-meters
        val dLat = GeoUtils.getLatitudeDistanceFromMeters(meters)
        val dLng = GeoUtils.getLongitudeDistanceFromMeters(lat, meters)

        val minLat = lat - dLat
        val maxLat = lat + dLat

        val minLng = lng - dLng
        val maxLng = lng + dLng

        return locationRepository.findAllByLatitudeLessThanEqualAndLatitudeGreaterThanEqualAndLongitudeLessThanEqualAndLongitudeGreaterThanEqualAndExpiryDateBefore(maxLat, minLat, maxLng, minLng, Instant.now())
    }

    fun createAndSaveLocationFromDto(dto: LocationDto): Location{
        val location = Location(
            null,
            dto.expiryDate,
            dto.latitude,
            dto.longitude,
            dto.locationType,
            dto.description
        )
        return locationRepository.save(location)
    }

    fun deleteById(id: Long): Unit = locationRepository.deleteById(id)

}