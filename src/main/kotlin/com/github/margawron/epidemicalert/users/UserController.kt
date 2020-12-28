package com.github.margawron.epidemicalert.users

import com.github.margawron.epidemicalert.notifications.PushService
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService,
    private val pushService: PushService
) {

    @GetMapping("users/self")
    @PreAuthorize("@permissionEvaluator.isUser(authentication)")
    fun getSelfData(authentication: Authentication): ResponseEntity<UserDto> {
        val user = userService.userFromAuth(authentication)
        val userDto = UserDto.fromEntity(user)
        return ResponseEntity.ok(userDto)
    }

    @GetMapping("/moderator")
    @PreAuthorize("@permissionEvaluator.isModerator(authentication)")
    fun moderator():String{
        return "hello moderator"
    }

    @GetMapping("/admin")
    @PreAuthorize("@permissionEvaluator.isAdmin(authentication)")
    fun admin():String{
        return "hello administrator"
    }

    @GetMapping("/test")
    @PreAuthorize("@permissionEvaluator.any()")
    fun test(): String{
        pushService.sendNotification()
        return "test"
    }
}