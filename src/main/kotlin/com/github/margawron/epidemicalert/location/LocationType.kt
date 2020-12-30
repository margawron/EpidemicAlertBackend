package com.github.margawron.epidemicalert.location

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException

enum class LocationType(val mapping: String) {
    INFO("I"),
    QUARANTINE("Q");

    companion object {
        fun fromDatabaseMapping(mapping: String) =
            LocationType.values().find { it.mapping == mapping }
                ?: throw ErrorCodeException(IllegalStateException::class, "internal.state.cannot_map", arrayOf(mapping))
    }
}
