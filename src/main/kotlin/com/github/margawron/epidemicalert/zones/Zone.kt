package com.github.margawron.epidemicalert.zones

import com.github.margawron.epidemicalert.converters.ZoneEmergencyLevelConverter
import java.time.Instant
import javax.persistence.*

@Entity
@Table(name = "t_zones")
class Zone (

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "zne_id")
    val id: Long? = null,

    @Column(name = "zne_name")
    val name: String,

    @Column(name = "zne_level")
    @Convert(converter = ZoneEmergencyLevelConverter::class)
    val zoneEmergencyLevel: ZoneEmergencyLevel,

    @Column(name = "zne_modification_date")
    val activeSince: Instant
)