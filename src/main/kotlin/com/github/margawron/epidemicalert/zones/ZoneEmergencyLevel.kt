package com.github.margawron.epidemicalert.zones

import com.github.margawron.epidemicalert.exceptions.KeyException

enum class ZoneEmergencyLevel(val mapping: String) {
    GREEN("G"),
    YELLOW("Y"),
    RED("R");

    companion object {
        fun fromDatabaseMapping(mapping: String) =
            values().find { it.mapping == mapping }
                ?: throw KeyException(IllegalStateException::class, "internal.state.cannot_map", arrayOf(mapping))
    }
}