package com.github.margawron.epidemicalert.measurements

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.margawron.epidemicalert.common.LatLng
import com.github.margawron.epidemicalert.device.Device
import com.github.margawron.epidemicalert.users.User
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "t_location_measurements")
data class Measurement(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "msr_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long? = null,

    @Column(name = "msr_timestamp")
    var timestamp: Instant,

    @Column(name = "msr_latitude", nullable = false)
    var latitude: Double,

    @Column(name = "msr_longitude")
    var longitude: Double,

    @Column(name = "msr_accuracy")
    var accuracy: Float,

    @Column(name = "msr_bearing")
    var bearing: Float,

    @Column(name = "msr_bearing_accuracy")
    var bearingAccuracy: Float,

    @ManyToOne
    @JoinColumn(name = "msr_usr_id", nullable = false)
    var ownerOfMeasurement: User,

    @ManyToOne
    @JoinColumn(name = "msr_dev_id", nullable = false)
    var originOfMeasurement: Device
) {
    fun toLatLng() = LatLng(latitude, longitude)
}