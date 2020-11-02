package com.github.margawron.epidemicalert.users

import javax.validation.constraints.NotBlank

class LoginDto(
        val login: String?,
        val email: String?,

        @NotBlank
        val password: String,
)