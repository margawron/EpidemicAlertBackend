package com.github.margawron.epidemicalert.users

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException
import com.github.margawron.epidemicalert.exceptions.ErrorCodeWithFieldException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.util.*
import javax.security.auth.login.CredentialException
import javax.validation.ValidationException

@Service
class UserService(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder) {

    @Transactional
    fun createAndSaveAccount(dto: RegistrationDto): User {
        if (userRepository.findByUserName(dto.login) != null) throw ErrorCodeWithFieldException(ValidationException::class, "user.username.exists", null, "login")
        if (userRepository.findByUserEmail(dto.email) != null) throw ErrorCodeWithFieldException(ValidationException::class, "user.email.exists", null, "email")

        return userRepository.save(
                User(
                        userName = dto.login,
                        passwordHash = passwordEncoder.encode(dto.password),
                        userEmail = dto.email
                )
        )
    }

    fun findAndCheckUserCredentials(loginDto: LoginDto): User {
        val user: User =  userRepository.findByUserName(loginDto.login) ?: throw ErrorCodeWithFieldException(NoSuchElementException::class, "user.username.not_exists", arrayOf(loginDto.login), "login")

        return if (passwordEncoder.matches(loginDto.password, user.passwordHash)) user else throw ErrorCodeWithFieldException(CredentialException::class, "user.credentials.notValid", null, "password")
    }

    fun findUserByUsername(username: String?): User {
        if (username == null) throw ErrorCodeException(IllegalArgumentException::class, "user.username.not_null")
        return userRepository.findByUserName(username)
                ?: throw ErrorCodeException(UsernameNotFoundException::class, "user.username.not_exists", arrayOf(username))
    }

    fun userFromAuth(auth: Authentication): User {
        val userPrincipal = auth.principal as UserPrincipal
        return userRepository.getOne(userPrincipal.id)
    }

}