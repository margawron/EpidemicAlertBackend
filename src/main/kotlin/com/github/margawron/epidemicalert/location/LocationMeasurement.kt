package com.github.margawron.epidemicalert.location

import com.github.margawron.epidemicalert.device.Device
import com.github.margawron.epidemicalert.users.User
import javax.persistence.*

@Entity
@Table(name = "t_location_measurements")
class LocationMeasurement(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "msr_id")
        val id: Long? = null,

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