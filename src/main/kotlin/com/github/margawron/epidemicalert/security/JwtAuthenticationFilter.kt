package com.github.margawron.epidemicalert.security

import com.github.margawron.epidemicalert.users.UserPrincipal
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.filter.OncePerRequestFilter
import javax.servlet.FilterChain
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

class JwtAuthenticationFilter(private val tokenService: JWTTokenService) : OncePerRequestFilter() {


    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        val authHeader = request.getHeader("Authorization")

        if (authorizationIsInvalid(authHeader)) {
            filterChain.doFilter(request, response)
            return
        }

        val token: UsernamePasswordAuthenticationToken = createToken(authHeader)
        SecurityContextHolder.getContext().authentication = token
        filterChain.doFilter(request, response)
    }

    private fun authorizationIsInvalid(authHeader: String?): Boolean = authHeader == null || !authHeader.startsWith("Bearer ")

    private fun createToken(authHeader: String): UsernamePasswordAuthenticationToken {
        val token = authHeader.replace("Bearer ", "")
        val user = tokenService.parseToken(token)

        val userPrincipal = UserPrincipal.fromUser(user)
        return UsernamePasswordAuthenticationToken(userPrincipal, null, setOf(user.role))
    }

}