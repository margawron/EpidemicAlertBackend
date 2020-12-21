package com.github.margawron.epidemicalert.proximity

import com.github.margawron.epidemicalert.exceptions.KeyException

enum class ProximityType(val mapping: String) {
    SUSPECT("S"),
    VICTIM("V");

    companion object {
        fun fromDatabaseMapping(mapping: String) =
            values().find { it.mapping == mapping }
                ?: throw KeyException(IllegalStateException::class, "internal.state.cannot_map", arrayOf(mapping))
    }

}