package com.github.margawron.epidemicalert.suspects

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException

enum class SuspicionLevel(val mapping: String) {
    SICK("S"),
    PROBABLE("P"),
    DISMISSED("D");

    companion object {
        fun fromDatabaseMapping(mapping: String) =
            values().find { it.mapping == mapping }
                ?: throw ErrorCodeException(IllegalStateException::class, "internal.suspicion_level.cannot_map", arrayOf(mapping))
    }

}