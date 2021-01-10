package com.github.margawron.epidemicalert.suspects

import com.github.margawron.epidemicalert.common.GeoUtils
import com.github.margawron.epidemicalert.common.LatLng
import com.github.margawron.epidemicalert.measurements.Measurement
import java.time.Duration

class MeasurementInterpolationIterator(
    first: Measurement,
    second: Measurement,
) : Iterator<LatLng> {

    companion object{
        private const val secondsPerIteration = 2
    }

    private var currentIteration = 0L
    private val firstLatLng = first.toLatLng()
    private val secondLatLng = second.toLatLng()

    private val secondsBetween: Long = Duration.between(first.timestamp, second.timestamp).seconds
    private val iterationsToBeDone = (secondsBetween / secondsPerIteration).toInt()

    private val distance = GeoUtils.getMetersDistanceBetween(firstLatLng, secondLatLng)
    private val iterationDistance = distance / iterationsToBeDone
    private val bearingInRad = GeoUtils.getBearingBetween(firstLatLng, secondLatLng)

    override fun hasNext(): Boolean {
        return currentIteration <= iterationsToBeDone
    }

    override fun next(): LatLng {
        val currentIterationDistance = iterationDistance*currentIteration
        currentIteration++
        return GeoUtils.getPointInDirectionToBearing(firstLatLng, bearingInRad, currentIterationDistance)
    }


}