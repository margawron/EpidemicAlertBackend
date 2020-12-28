package com.github.margawron.epidemicalert.security

import com.fasterxml.jackson.annotation.JsonProperty
import java.util.*

class LoginResponse(
    @JsonProperty(value = "access_token")
    val accessToken: String,

    @JsonProperty(value = "expiration_date")
    val expirationDate: Date,

    @JsonProperty(value = "device_id")
    val deviceId: Long,

    @JsonProperty(value = "token_type")
    val tokenType: String = "Bearer"

)