package com.github.margawron.epidemicalert.device

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException
import com.github.margawron.epidemicalert.users.UserService
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class DeviceController(
    private val userService: UserService,
    private val deviceService: DeviceService
) {

    @PostMapping("device/{id}/fcmToken")
    @PreAuthorize("@permissionEvaluator.isRegistered(authentication)")
    fun updateFirebaseToken(
        authentication: Authentication,
        @PathVariable("id") deviceId: Long,
        @RequestBody firebaseToken: String
    ): DeviceDto {
        val user = userService.userFromAuth(authentication)
        val device = user.userDevices.find { it.id == deviceId } ?: throw ErrorCodeException(
            this::class,
            "device.given_id_does_not_exist"
        )
        device.firebaseToken = firebaseToken
        val savedDevice = deviceService.saveDevice(device)
        return DeviceDto.fromDevice(savedDevice)
    }
}