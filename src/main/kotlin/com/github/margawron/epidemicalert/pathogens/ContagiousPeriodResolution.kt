package com.github.margawron.epidemicalert.pathogens

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException
import java.time.temporal.ChronoUnit

enum class ContagiousPeriodResolution(val mapping: String, val chronoUnit: ChronoUnit) {
    MINUTES("M", ChronoUnit.MINUTES),
    HOURS("H", ChronoUnit.HOURS),
    DAYS("D", ChronoUnit.DAYS);

    companion object {
        fun fromDatabaseMapping(mapping: String) =
            values().find { it.mapping == mapping }
                ?: throw ErrorCodeException(IllegalStateException::class, "internal.contagious_period_resolution.cannot_map", arrayOf(mapping))
    }
}