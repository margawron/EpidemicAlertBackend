package com.github.margawron.epidemicalert.users

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.margawron.epidemicalert.alerts.Alert
import com.github.margawron.epidemicalert.converters.AccountStateConverter
import com.github.margawron.epidemicalert.converters.RoleConverter
import com.github.margawron.epidemicalert.device.Device
import com.github.margawron.epidemicalert.measurements.Measurement
import com.github.margawron.epidemicalert.suspects.Suspect
import java.time.Instant
import javax.persistence.*


@Entity
@Table(name = "t_users")
data class User(

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "usr_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long? = null,

    @Column(name = "usr_username", nullable = false)
    var userName: String,

    @Column(name = "usr_password_hash", nullable = false)
    var passwordHash: String,

    @Column(name = "usr_mail", nullable = false)
    var userEmail: String,

    @Convert(converter = RoleConverter::class)
    @Column(name = "usr_permission_group", nullable = false)
    var role: Role = Role.USER,

    @Column(name = "usr_account_creation_date")
    var accountCreationDate: Instant? = Instant.now(),

    @Column(name = "usr_account_expiry_date")
    var accountExpirationDate: Instant? = null,

    @Convert(converter = AccountStateConverter::class)
    @Column(name = "usr_account_state", nullable = false)
    var accountState: AccountState = AccountState.NORMAL,

    @OneToMany(mappedBy = "deviceOwner")
    var userDevices: MutableList<Device> = mutableListOf(),

    @OneToMany(mappedBy = "ownerOfMeasurement")
    var userMeasurementHistory: MutableList<Measurement> = mutableListOf(),

    @OneToMany(mappedBy = "victim")
    var userAlerts: MutableList<Alert> = mutableListOf(),

    @OneToMany(mappedBy = "suspect")
    var userSuspicions: MutableList<Suspect> = mutableListOf()
)
