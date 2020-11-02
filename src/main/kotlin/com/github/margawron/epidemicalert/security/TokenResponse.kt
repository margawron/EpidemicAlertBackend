package com.github.margawron.epidemicalert.security

import com.fasterxml.jackson.annotation.JsonProperty

class TokenResponse(
        @JsonProperty(value = "access_token")
        val accessToken: String
) {
    @JsonProperty(value = "token_type")
    val tokenType: String = "Bearer"
}