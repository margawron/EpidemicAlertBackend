package com.github.margawron.epidemicalert.alerts

import com.github.margawron.epidemicalert.users.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class AlertController(
    private val alertService: AlertService,
    private val userService: UserService,
) {

    @GetMapping("alert/{id}")
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun getSingleAlert(@PathVariable("id") alertId: Long, auth: Authentication): AlertDto {
        val user = userService.userFromAuth(auth)
        val alert = alertService.getAlertById(alertId)
        return AlertDto.fromEntityForUser(alert, user)
    }

    @GetMapping("alert/all")
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun getAllAlertsForUser(authentication: Authentication): List<AlertDto> {
        val user = userService.userFromAuth(authentication)
        val alerts = user.userAlerts
        return alerts.map { AlertDto.fromEntityForUser(it, user) }
    }

}