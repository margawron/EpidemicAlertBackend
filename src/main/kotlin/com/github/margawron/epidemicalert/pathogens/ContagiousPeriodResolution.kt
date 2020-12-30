package com.github.margawron.epidemicalert.pathogens

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException

enum class ContagiousPeriodResolution(val mapping: String) {
    MINUTES("M"),
    HOURS("H"),
    DAYS("D");

    companion object {
        fun fromDatabaseMapping(mapping: String) =
            values().find { it.mapping == mapping }
                ?: throw ErrorCodeException(IllegalStateException::class, "internal.state.cannot_map", arrayOf(mapping))
    }
}