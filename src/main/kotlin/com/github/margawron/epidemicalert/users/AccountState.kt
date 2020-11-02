package com.github.margawron.epidemicalert.users

import com.github.margawron.epidemicalert.exceptions.MessageKeyException

enum class AccountState(val mapping: String) {
    NORMAL("N"),
    BANNED("B");

    companion object {
        fun fromDatabaseMapping(mapping: String) =
                values().find { it.mapping == mapping }
                        ?: throw MessageKeyException(IllegalStateException::class, "internal.state.cannot_map", arrayOf(mapping))
    }
}