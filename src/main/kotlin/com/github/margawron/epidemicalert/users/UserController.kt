package com.github.margawron.epidemicalert.users

import com.github.margawron.epidemicalert.notifications.PushService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
    private val pushService: PushService
) {

    @GetMapping("users/self")
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun getSelfData(authentication: Authentication): UserDto {
        val user = userService.userFromAuth(authentication)
        return UserDto.fromEntity(user)
    }

    @GetMapping("users/name/{name}")
    @PreAuthorize("@permissionEvaluator.isAtLeastModerator(authentication)")
    fun getUsersWithNameLike(@PathVariable("name") name:String): List<UserDto> {
        return userService.getUsersWithStringInName(name).map {
            UserDto.fromEntity(it)
        }
    }

    @GetMapping("/test")
    @PreAuthorize("@permissionEvaluator.any()")
    fun test(): String{
        pushService.sendNotification()
        return "test"
    }
}