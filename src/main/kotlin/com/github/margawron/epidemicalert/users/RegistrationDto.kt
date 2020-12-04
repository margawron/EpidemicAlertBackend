package com.github.margawron.epidemicalert.users

import io.swagger.annotations.ApiModel
import javax.validation.constraints.Email
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.Size

@ApiModel(value= "Register Request")
class RegistrationDto(

        @NotEmpty(message = "{login.required}")
        @Size(min = 5, max = 32, message = "{login.requirements}")
        var login: String,

        @NotEmpty(message = "{password.required}")
        @Size(min = 5, max = 32, message = "{password.requirements}")
        var password: String,

        @NotEmpty(message = "{email.required}")
        @Email(message = "{email.requirements}")
        var email: String
)