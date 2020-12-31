package com.github.margawron.epidemicalert.proximity

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException

enum class ProximityType(val mapping: String) {
    SUSPECT("S"),
    VICTIM("V");

    companion object {
        fun fromDatabaseMapping(mapping: String) =
            values().find { it.mapping == mapping }
                ?: throw ErrorCodeException(IllegalStateException::class, "internal.proximity_type.cannot_map", arrayOf(mapping))
    }

}