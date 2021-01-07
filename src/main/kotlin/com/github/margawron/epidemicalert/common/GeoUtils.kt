package com.github.margawron.epidemicalert.common

import com.github.margawron.epidemicalert.measurements.Measurement
import kotlin.math.*

object GeoUtils {

    private const val earthRadius: Int = 6_371_000 // approx. in meters
    private const val radToDeg = (180 / PI)
    private const val degToRad = (PI / 180)

    fun getLatitudeDistanceFromMeters(meters: Double): Double {
        return (meters / earthRadius) * radToDeg
    }
    
    fun getLongitudeDistanceFromMeters(lat:Double, meters: Double): Double {
        return (meters / earthRadius) * radToDeg / cos(lat * degToRad)
    }

    fun getMetersDistanceBetween(first: LatLng, second: LatLng): Double{
        val lat1 = first.latitude * degToRad
        val lat2 = second.latitude * degToRad
        val dLat = (first.latitude - second.latitude) * degToRad
        val dLng = (first.longitude - second.longitude) * degToRad

        // Kwadrat połowy długości cięciwy
        val halvedLengthChordSquared = sin(dLat / 2) * sin(dLng / 2) +
                cos(lat1) * cos(lat2) +
                sin(dLng / 2) * sin(dLng / 2)
        val angularDistance = 2 * atan2(sqrt(halvedLengthChordSquared), sqrt(1-halvedLengthChordSquared))
        return earthRadius * angularDistance
    }

    fun getBearingBetween(first: LatLng, second: LatLng): Double {
        val x = sin(second.longitude - first.longitude) * cos(second.latitude)
        val y = cos(first.latitude) * sin(second.latitude) -
                sin(first.latitude) * cos(second.latitude) * cos(second.longitude-first.longitude)
        val bearingInRad = atan2(y, x)
        return bearingInRad
    }

    fun getPointInDirectionToBearing(measurement: LatLng, bearing: Double, distanceInMeters: Double): LatLng {
        val latOther = asin(
            sin(measurement.latitude) * cos(distanceInMeters / earthRadius) +
                    cos(measurement.latitude) * sin(distanceInMeters / earthRadius) * cos(bearing)
        )
        val lngOther = measurement.longitude + atan2(
            sin(bearing) * sin(distanceInMeters / earthRadius) * cos(measurement.latitude),
            cos(distanceInMeters / earthRadius) - sin(measurement.latitude) * sin(latOther)
        )
        return LatLng(latOther, lngOther)
    }

}

