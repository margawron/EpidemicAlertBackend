package com.github.margawron.epidemicalert.users

import com.github.margawron.epidemicalert.notifications.PushService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

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

    @PostMapping("users/id/{id}")
    @PreAuthorize("@permissionEvaluator.isAdmin(authentication)")
    fun changeUserRole(@PathVariable("id")userId: Long, @RequestBody role: Role): UserDto {
        val user = userService.findUserById(userId)
        user.role = role
        val savedUser = userService.saveUser(user)
        return UserDto.fromEntity(savedUser)
    }

    @GetMapping("/test")
    @PreAuthorize("@permissionEvaluator.any()")
    fun test(): String{
        pushService.sendNotification()
        return "test"
    }
}