package com.github.margawron.epidemicalert.users

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException
import org.springframework.security.core.GrantedAuthority

enum class Role(private val mapping: String) : GrantedAuthority {
    USER("U"),
    MODERATOR("M"),
    ADMINISTRATOR("A");

    override fun getAuthority(): String = mapping

    companion object {
        fun fromDatabaseMapping(mapping: String): Role =
                values().find { it.mapping == mapping }
                        ?: throw ErrorCodeException(IllegalStateException::class, "internal.role.cannot_map", arrayOf(mapping))
    }
}