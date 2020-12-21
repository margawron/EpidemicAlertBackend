package com.github.margawron.epidemicalert.measurements

import com.github.margawron.epidemicalert.device.Device
import com.github.margawron.epidemicalert.users.User
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "t_location_measurements")
class Measurement(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "msr_id")
        val id: Long? = null,

        @Column(name = "msr_timestamp")
        var timestamp: Instant,

        @Column(name = "msr_latitude", nullable = false)
        var latitude: Float,

        @Column(name = "msr_longitude")
        var longitude: Float,

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
)