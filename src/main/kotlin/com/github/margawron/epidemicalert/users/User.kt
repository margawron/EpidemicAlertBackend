package com.github.margawron.epidemicalert.users

import com.github.margawron.epidemicalert.converters.AccountStateConverter
import com.github.margawron.epidemicalert.converters.RoleConverter
import com.github.margawron.epidemicalert.device.Device
import com.github.margawron.epidemicalert.location.LocationMeasurement
import java.time.LocalDateTime
import javax.persistence.*


@Entity
@Table(name = "t_users")
class User(

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        @Column(name = "usr_id")
        val id: Long? = null,

        @Column(name = "usr_username", nullable = false)
        var userName: String,

        @Column(name = "usr_password", nullable = false)
        var passwordHash: String,

        @Column(name = "usr_mail", nullable = false)
        var userEmail: String,

        @Convert(converter = RoleConverter::class)
        @Column(name = "usr_permission_group", nullable = false)
        var role: Role = Role.USER,

        @Column(name = "usr_account_creation_date")
        var accountCreationDate: LocalDateTime? = LocalDateTime.now(),

        @Column(name = "usr_account_expiry_date", nullable = true)
        var accountExpirationDate: LocalDateTime? = null,

        @Convert(converter = AccountStateConverter::class)
        @Column(name = "usr_account_state", nullable = false)
        var accountState: AccountState = AccountState.NORMAL,

        @OneToMany(mappedBy = "deviceOwner")
        var userDevices: Set<Device> = emptySet(),

        @OneToMany(mappedBy = "ownerOfMeasurement")
        var userLocationMeasurementHistory: Set<LocationMeasurement> = emptySet()

)