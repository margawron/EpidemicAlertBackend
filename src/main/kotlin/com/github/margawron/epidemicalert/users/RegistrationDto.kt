package com.github.margawron.epidemicalert.users

import io.swagger.annotations.ApiModel
import javax.validation.constraints.Email
import javax.validation.constraints.NotBlank
import javax.validation.constraints.Size

@ApiModel(value = "Register Request")
class RegistrationDto(

    @field:NotBlank(message = "{login.required}")
    @field:Size(min = 5, max = 12, message = "{login.requirements}")
    var login: String,

    @field:NotBlank(message = "{password.required}")
    @field:Size(min = 5, max = 32, message = "{password.requirements}")
    var password: String,

    @field:NotBlank(message = "{email.required}")
    @field:Size(min = 5, max = 30, message = "{email.requirements}")
    @field:Email(regexp = "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9][a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9][a-zA-Z0-9\\-]{0,25})+", message = "{email.wrong_format}")
    var email: String
)