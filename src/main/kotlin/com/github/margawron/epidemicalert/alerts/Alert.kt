package com.github.margawron.epidemicalert.alerts

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.margawron.epidemicalert.proximity.ProximityMeasurement
import com.github.margawron.epidemicalert.suspects.Suspect
import com.github.margawron.epidemicalert.users.User
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "t_alerts")
data class Alert (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alr_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long?,

    @JoinColumn(name = "alr_sus_id")
    @ManyToOne
    val suspect: Suspect,

    @JoinColumn(name = "alr_usr_id")
    @ManyToOne
    val victim: User,

    @Column(name = "alr_creation_instant")
    val creationInstant: Instant,

    @OneToMany(mappedBy = "alert", cascade = [CascadeType.ALL])
    val proximityMeasurements: MutableList<ProximityMeasurement> = mutableListOf()
)