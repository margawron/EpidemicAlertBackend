package com.github.margawron.epidemicalert.security

import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.config.web.servlet.invoke
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class SecurityConfig(private val tokenService: JWTTokenService) : WebSecurityConfigurerAdapter() {

    companion object {
        val SWAGGER_UI_ANT_MATCHERS = arrayOf(
            "/swagger-ui/**",
            "/webjars/**",
            "/v2/**",
            "/swagger-resources/**"
        )
    }

    override fun configure(http: HttpSecurity) {
        http {
            csrf {
                disable()
            }
            sessionManagement {
                sessionCreationPolicy = SessionCreationPolicy.STATELESS
            }
            authorizeRequests {
                authorize("/register/**", permitAll)
                authorize("/auth/**", permitAll)
                SWAGGER_UI_ANT_MATCHERS.forEach { authorize(it, permitAll) }
                authorize(anyRequest, authenticated)
            }
            addFilterAt(JwtAuthenticationFilter(tokenService), BasicAuthenticationFilter::class.java)
        }
    }
}