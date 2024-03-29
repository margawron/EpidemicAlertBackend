package com.github.margawron.epidemicalert.device

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.margawron.epidemicalert.measurements.Measurement
import com.github.margawron.epidemicalert.users.User
import javax.persistence.*

@Entity
@Table(name = "t_devices")
data class Device(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "dev_id", nullable = false)
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long? = null,

    @Column(name = "dev_manufacturer", nullable = false)
    var manufacturer: String,

    @Column(name = "dev_firebase_token")
    var firebaseToken: String,

    @Column(name = "dev_name", nullable = false)
    var deviceName: String,

    @Column(name = "dev_serial_number", nullable = false)
    var serialNumber: String,

    @ManyToOne
    @JoinColumn(name = "dev_usr_id")
    var deviceOwner: User,

    @OneToMany(mappedBy = "originOfMeasurement")
    var deviceMeasurements: MutableList<Measurement> = mutableListOf()
)