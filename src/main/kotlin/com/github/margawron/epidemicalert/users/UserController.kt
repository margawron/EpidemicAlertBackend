package com.github.margawron.epidemicalert.users

import com.github.margawron.epidemicalert.security.PermissionEvaluator
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(private val permissionEvaluator: PermissionEvaluator) {

    @GetMapping("/user")
    @PreAuthorize("@permissionEvaluator.isUser(authentication)")
    fun test(): String {
        return "hello user"
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