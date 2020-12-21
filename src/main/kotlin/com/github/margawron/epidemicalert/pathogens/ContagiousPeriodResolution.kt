package com.github.margawron.epidemicalert.pathogens

import com.github.margawron.epidemicalert.exceptions.KeyException

enum class ContagiousPeriodResolution(val mapping: String) {
    MINUTES("M"),
    HOURS("H"),
    DAYS("D");

    companion object {
        fun fromDatabaseMapping(mapping: String) =
            values().find { it.mapping == mapping }
                ?: throw KeyException(IllegalStateException::class, "internal.state.cannot_map", arrayOf(mapping))
    }
}