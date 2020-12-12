package com.github.margawron.epidemicalert.users

import com.github.margawron.epidemicalert.exceptions.KeyException
import com.github.margawron.epidemicalert.exceptions.KeyWithFieldException
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*
import javax.security.auth.login.CredentialException
import javax.transaction.Transactional
import javax.validation.ValidationException

@Service
class UserService(
        private val userRepository: UserRepository,
        private val passwordEncoder: PasswordEncoder) {

    @Transactional
    fun createAndSaveAccount(dto: RegistrationDto): User {
        if (userRepository.findByUserName(dto.login) != null) throw KeyWithFieldException(ValidationException::class, "user.username.exists", null, "login")
        if (userRepository.findByUserEmail(dto.email) != null) throw KeyWithFieldException(ValidationException::class, "user.email.exists", null, "email")

        return userRepository.save(
                User(
                        userName = dto.login,
                        passwordHash = passwordEncoder.encode(dto.password),
                        userEmail = dto.email
                )
        )
    }

    fun findAndCheckUserCredentials(loginDto: LoginDto): User {
        val user: User =  userRepository.findByUserName(loginDto.login) ?: throw KeyWithFieldException(NoSuchElementException::class, "user.username.not_exists", arrayOf(loginDto.login), "login")

        return if (passwordEncoder.matches(loginDto.password, user.passwordHash)) user else throw KeyWithFieldException(CredentialException::class, "user.credentials.notValid", null, "password")
    }

    fun findUserByUsername(username: String?): User {
        if (username == null) throw KeyException(IllegalArgumentException::class, "user.username.not_null")
        return userRepository.findByUserName(username)
                ?: throw KeyException(UsernameNotFoundException::class, "user.username.not_exists", arrayOf(username))
    }
}