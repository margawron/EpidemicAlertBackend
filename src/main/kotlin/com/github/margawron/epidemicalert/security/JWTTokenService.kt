package com.github.margawron.epidemicalert.security

import com.github.margawron.epidemicalert.users.User
import com.github.margawron.epidemicalert.users.UserService
import io.jsonwebtoken.ExpiredJwtException
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.MessageSource
import org.springframework.stereotype.Service
import java.time.Instant
import java.time.temporal.ChronoUnit
import java.util.*

@Service
class JWTTokenService(
    private val userService: UserService,

    private val messageSource: MessageSource,

    @Value("\${epidemicalert.token.expiryTime}")
    private val expirySeconds: Long,

    @Value("\${JWT_SECRET}")
    secret: String
) {

    private val JWT_SECRET_BYTES = secret.toByteArray()

    fun generateToken(user: User): TokenResponse {
        val expirationDate = Date.from(Instant.now().plus(expirySeconds, ChronoUnit.SECONDS))
        val key = Keys.hmacShaKeyFor(JWT_SECRET_BYTES)

        val token = Jwts.builder()
            .claim("id", user.id)
            .claim("username", user.userName)
            .claim("role", user.role)
            .setExpiration(expirationDate)
            .signWith(key, SignatureAlgorithm.HS512)
            .compact()
        return TokenResponse("Bearer $token", expirationDate)
    }

    fun parseToken(token: String): User {
        val jwsClaims = Jwts.parserBuilder()
            .setSigningKey(JWT_SECRET_BYTES)
            .build()
            .parseClaimsJws(token)

        val tokenExpirationDate = jwsClaims.body.expiration

        return if (tokenExpirationDate.after(Date.from(Instant.now()))) userService.findUserByUsername(
            jwsClaims.body.get(
                "username",
                String::class.java
            )
        )
        else throw ExpiredJwtException(
            jwsClaims.header,
            jwsClaims.body,
            messageSource.getMessage("token.expired", null, Locale.getDefault())
        )
    }
}