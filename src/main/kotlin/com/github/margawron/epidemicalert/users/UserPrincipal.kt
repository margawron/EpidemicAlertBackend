package com.github.margawron.epidemicalert.users

import java.security.Principal

class UserPrincipal(val id: Long, private val name: String) : Principal {
    override fun getName(): String = this.name

    companion object{
        fun fromUser(user: User): UserPrincipal = UserPrincipal(user.id!!, user.userName)
    }
}