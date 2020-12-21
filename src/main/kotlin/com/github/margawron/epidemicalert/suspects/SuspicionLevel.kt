package com.github.margawron.epidemicalert.suspects

import com.github.margawron.epidemicalert.exceptions.KeyException

enum class SuspicionLevel(val mapping: String) {
    SICK("S"),
    PROBABLE("P"),
    DISMISSED("D");

    companion object {
        fun fromDatabaseMapping(mapping: String) =
            values().find { it.mapping == mapping }
                ?: throw KeyException(IllegalStateException::class, "internal.state.cannot_map", arrayOf(mapping))
    }

}