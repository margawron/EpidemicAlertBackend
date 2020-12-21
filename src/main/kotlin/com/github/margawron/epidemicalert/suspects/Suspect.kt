package com.github.margawron.epidemicalert.suspects

import com.github.margawron.epidemicalert.converters.SuspicionLevelConverter
import com.github.margawron.epidemicalert.pathogens.Pathogen
import com.github.margawron.epidemicalert.users.User
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "t_suspects")
class Suspect (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "sus_id")
    val id: Long?,

    @Column(name = "sus_start_time")
    val startTime: Instant,

    @Column(name = "sus_suspiciousness")
    @Convert(converter = SuspicionLevelConverter::class)
    val suspicionLevel: SuspicionLevel,

    @Column(name = "sus_usr_id")
    @ManyToOne(targetEntity = User::class)
    val suspect: User,

    @Column(name = "sus_pat_id")
    @ManyToOne(targetEntity = Pathogen::class)
    val pathogen: Pathogen
)