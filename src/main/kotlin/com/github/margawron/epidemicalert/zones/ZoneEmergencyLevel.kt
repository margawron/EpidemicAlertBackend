package com.github.margawron.epidemicalert.zones

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException

enum class ZoneEmergencyLevel(val mapping: String) {
    GREEN("G"),
    YELLOW("Y"),
    RED("R");

    companion object {
        fun fromDatabaseMapping(mapping: String) =
            values().find { it.mapping == mapping }
                ?: throw ErrorCodeException(IllegalStateException::class, "internal.zone_emergency_level.cannot_map", arrayOf(mapping))
    }
}