package com.github.margawron.epidemicalert.common

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
        val dLat = (second.latitude - first.latitude) * degToRad
        val dLng = (second.longitude - first.longitude) * degToRad

        // Kwadrat połowy długości cięciwy
        val a = sin(dLat / 2) * sin(dLat / 2) + cos(lat1) * cos(lat2) * sin(dLng / 2) * sin(dLng / 2)
        // Zmiana kąta (droga/kąt)
        val c = 2 * atan2(sqrt(a), sqrt(1-a))
        return earthRadius * c
    }
    //
    fun getBearingBetween(first: LatLng, second: LatLng): Double {
        val lat1 = first.latitude * degToRad
        val lon1 = first.longitude * degToRad
        val lat2 = second.latitude * degToRad
        val lon2 = second.longitude * degToRad
        val x = sin(lon2 - lon1) * cos(lat2)
        val y = cos(lat1) * sin(lat2) - sin(lat1) * cos(lat2) * cos(lon2 - lon1)
        val bearingInRad = atan2(y, x)
        return bearingInRad
    }

    fun getPointInDirectionOfBearing(measurement: LatLng, bearingRad: Double, distanceInMeters: Double): LatLng {
        val lat = measurement.latitude * degToRad
        val lon = measurement.longitude * degToRad
        val otherLat = asin(
            sin(lat) * cos(distanceInMeters / earthRadius) +
                    cos(lat) * sin(distanceInMeters / earthRadius) * cos(bearingRad)
        )
        val otherLng = lon + atan2(
            sin(bearingRad) * sin(distanceInMeters / earthRadius) * cos(lat),
            cos(distanceInMeters / earthRadius) - sin(lat) * sin(otherLat)
        )
        return LatLng(otherLat * radToDeg, otherLng * radToDeg)
    }

}

