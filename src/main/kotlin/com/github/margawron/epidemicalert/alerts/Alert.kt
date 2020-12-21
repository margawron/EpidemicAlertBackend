package com.github.margawron.epidemicalert.alerts

import com.github.margawron.epidemicalert.suspects.Suspect
import com.github.margawron.epidemicalert.users.User
import javax.persistence.*

@Entity
@Table(name = "t_alerts")
class Alert (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "alr_id")
    val id: Long?,

    @JoinColumn(name = "alr_sus_id")
    @ManyToOne
    val suspect: Suspect,

    @JoinColumn(name = "alr_usr_id")
    @ManyToOne
    val victim: User
)