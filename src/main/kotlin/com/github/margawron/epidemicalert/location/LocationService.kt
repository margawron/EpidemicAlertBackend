package com.github.margawron.epidemicalert.location

import org.springframework.stereotype.Service
import kotlin.math.PI
import kotlin.math.cos



@Service
class LocationService(
    private val locationRepository: LocationRepository
) {

    companion object{
        const val earthRadius: Int = 6_371_000 // approx. in meters
        const val radToDeg = (180 / PI)
        const val degToRad = (PI / 180)
    }

    fun getLocationsFromLatLngInDistanceOf(lat: Double, lng: Double, meters: Double): List<Location> {
        // https://www.rapidtables.com/convert/number/degrees-to-degrees-minutes-seconds.html
        // http://www.movable-type.co.uk/scripts/latlong.html
        // https://stackoverflow.com/questions/7477003/calculating-new-longitude-latitude-from-old-n-meters
        val dLat = (meters / earthRadius) * radToDeg
        val dLng = (meters / earthRadius) * radToDeg / cos(lat * degToRad)

        val minLat = lat - dLat
        val maxLat = lat + dLat

        val minLng = lng - dLng
        val maxLng = lng + dLng

        return locationRepository.findAllByLatitudeLessThanEqualAndLatitudeGreaterThanEqualAndLongitudeLessThanEqualAndLongitudeGreaterThanEqual(maxLat, minLat, maxLng, minLng)
    }

}