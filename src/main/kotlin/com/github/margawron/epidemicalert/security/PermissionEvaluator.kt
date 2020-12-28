package com.github.margawron.epidemicalert.security

import com.github.margawron.epidemicalert.users.Role
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Component

@Component
class PermissionEvaluator {

    fun isRegistered(auth: Authentication) = isUser(auth) || isModerator(auth) || isAdmin(auth)

    fun isUser(auth: Authentication): Boolean = auth.authorities.contains(Role.USER)

    fun isModerator(auth: Authentication): Boolean = auth.authorities.contains(Role.MODERATOR)

    fun isAtLeastModerator(auth: Authentication) = isModerator(auth) || isAdmin(auth)

    fun isAdmin(auth: Authentication): Boolean = auth.authorities.contains(Role.ADMINISTRATOR)

    @Deprecated(message = "Debug only.", replaceWith = ReplaceWith("false"))
    fun any(): Boolean = true
}