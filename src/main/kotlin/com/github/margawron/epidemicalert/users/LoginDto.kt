package com.github.margawron.epidemicalert.users

import io.swagger.annotations.ApiModel
import javax.validation.constraints.NotBlank

@ApiModel(value="Login Request")
class LoginDto(
        val login: String?,
        val email: String?,

        @NotBlank
        val password: String,
)