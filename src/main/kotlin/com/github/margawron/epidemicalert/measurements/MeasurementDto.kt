package com.github.margawron.epidemicalert.measurements

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant
import javax.validation.constraints.DecimalMax
import javax.validation.constraints.DecimalMin

class MeasurementDto (
    @field:JsonProperty(value = "id")
    val clientSideId: Long,

    val measurementTime: Instant,

    @field:DecimalMax(value = "90.0")
    @field:DecimalMin(value = "-90.0")
    val latitude: Double,

    @field:DecimalMax(value = "180.0")
    @field:DecimalMin(value = "-180.0")
    val longitude: Double,

    @field:DecimalMax(value = "22.0")
    val accuracy: Float,

    val bearing: Float,
    val bearingAccuracy: Float,
)