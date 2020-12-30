package com.github.margawron.epidemicalert.users

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException

enum class AccountState(val mapping: String) {
    NORMAL("N"),
    BANNED("B");

    companion object {
        fun fromDatabaseMapping(mapping: String) =
                values().find { it.mapping == mapping }
                        ?: throw ErrorCodeException(IllegalStateException::class, "internal.state.cannot_map", arrayOf(mapping))
    }
}