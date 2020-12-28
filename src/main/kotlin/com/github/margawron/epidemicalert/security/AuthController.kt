package com.github.margawron.epidemicalert.security

import com.github.margawron.epidemicalert.device.DeviceService
import com.github.margawron.epidemicalert.users.LoginDto
import com.github.margawron.epidemicalert.users.RegistrationDto
import com.github.margawron.epidemicalert.users.UserDto
import com.github.margawron.epidemicalert.users.UserService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
class AuthController(private val userService: UserService,
                     private val deviceService: DeviceService,
                     private val tokenService: JWTTokenService) {

    @PostMapping(value = ["/register/"])
    fun register(@Valid @RequestBody registrationRequest: RegistrationDto): ResponseEntity<UserDto> {
        val createdUser = userService.createAndSaveAccount(registrationRequest)
        val userDto = UserDto.fromEntity(createdUser)
        return ResponseEntity.ok().body(userDto)
    }

    @PostMapping(value = ["/auth/"])
    fun generateToken(@Valid @RequestBody loginRequest: LoginDto): LoginResponse {
        val user = userService.findAndCheckUserCredentials(loginRequest)
        val device = deviceService.registerDeviceOrUpdateFirebaseToken(user, loginRequest)
        return tokenService.generateToken(user, device)
    }
}