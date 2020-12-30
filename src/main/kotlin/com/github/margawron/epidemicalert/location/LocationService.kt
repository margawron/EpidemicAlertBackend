package com.github.margawron.epidemicalert.location

import org.springframework.stereotype.Service
import kotlin.math.PI
import kotlin.math.cos

const val earthRadius: Int = 6_371_000 // meters


@Service
class LocationService(
    private val locationRepository: LocationRepository
) {

    fun getLocationsFromLatLngInDistanceOf(lat: Double, lng: Double, meters: Double): List<Location> {
        // https://www.rapidtables.com/convert/number/degrees-to-degrees-minutes-seconds.html
        // http://www.movable-type.co.uk/scripts/latlong.html
        // https://stackoverflow.com/questions/7477003/calculating-new-longitude-latitude-from-old-n-meters
        val dLat = (meters / earthRadius) * (180 / PI)
        val dLng = (meters / earthRadius) * (180 / PI) / cos(lat * PI / 180)

        val minLat = lat - dLat
        val maxLat = lat + dLat

        val minLng = lng - dLng
        val maxLng = lng + dLng

        return locationRepository.findAllByLatitudeLessThanEqualAndLatitudeGreaterThanEqualAndLongitudeLessThanEqualAndLongitudeGreaterThanEqual(maxLat, minLat, maxLng, minLng)
    }

}