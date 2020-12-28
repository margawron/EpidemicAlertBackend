package com.github.margawron.epidemicalert.users

import io.swagger.annotations.ApiModel
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@ApiModel(value="Login Request")
class LoginDto(
        @field:NotBlank(message = "{login.required}")
        @field:Size(min = 5, max = 12, message = "{login.requirements}")
        val login: String,

        @field:NotBlank(message = "{password.required}")
        @field:Size(min = 5, max = 32, message = "{password.requirements}")
        val password: String,

        val fcmToken: String,

        val serialNumber: String,

        val manufacturer: String,

        val deviceName: String,

)