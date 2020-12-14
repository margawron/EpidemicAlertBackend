package com.github.margawron.epidemicalert.users

import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {

    @GetMapping("users/self")
    @PreAuthorize("@permissionEvaluator.isUser(authentication)")
    fun getSelfData(authentication: Authentication): ResponseEntity<UserDto> {
        return ResponseEntity.ok(UserDto.fromEntity(authentication.principal as User))
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

}