package com.github.margawron.epidemicalert.suspects

import com.fasterxml.jackson.annotation.JsonProperty
import com.github.margawron.epidemicalert.alerts.Alert
import com.github.margawron.epidemicalert.converters.SuspicionLevelConverter
import com.github.margawron.epidemicalert.pathogens.Pathogen
import com.github.margawron.epidemicalert.users.User
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "t_suspects")
data class Suspect (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sus_id")
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    val id: Long?,

    @Column(name = "sus_start_time")
    val startTime: Instant,

    @Column(name = "sus_suspiciousness")
    @Convert(converter = SuspicionLevelConverter::class)
    val suspicionLevel: SuspicionLevel,

    @JoinColumn(name = "sus_usr_id")
    @ManyToOne(targetEntity = User::class)
    val suspect: User,

    @JoinColumn(name = "sus_pat_id")
    @ManyToOne(targetEntity = Pathogen::class)
    val pathogen: Pathogen,

    @OneToMany(mappedBy = "suspect", cascade = [CascadeType.PERSIST])
    val alerts: MutableList<Alert> = mutableListOf()
)