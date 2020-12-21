package com.github.margawron.epidemicalert.pathogens

import com.github.margawron.epidemicalert.converters.ContagiousPeriodResolutionConverter
import javax.persistence.*


@Entity
@Table(name = "t_pathogens")
class Pathogen (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "pat_id")
    val id: Long?,

    @Column(name = "pat_name")
    val name: String,

    @Column(name = "pat_contagious_period")
    val contagiousPeriod: Int,

    @Column(name = "pat_period_resolution")
    @Convert(converter = ContagiousPeriodResolutionConverter::class)
    val contagiousPeriodResolution: ContagiousPeriodResolution,

    @Column(name = "pat_detection_range")
    val detectionRange: Float,

    @Column(name = "pat_accuracy")
    val accuracy: Float
)