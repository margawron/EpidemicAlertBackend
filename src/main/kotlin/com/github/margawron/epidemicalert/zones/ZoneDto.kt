package com.github.margawron.epidemicalert.zones

import com.fasterxml.jackson.annotation.JsonProperty
import java.time.Instant

data class ZoneDto(
    var id: Long? = null,
    val name: String,
    val zoneEmergencyLevel: ZoneEmergencyLevel,
    val activeSince: Instant
){
    companion object{
        fun fromZone(zone: Zone) = ZoneDto(
            zone.id,
            zone.name,
            zone.zoneEmergencyLevel,
            zone.activeSince
        )
    }
}