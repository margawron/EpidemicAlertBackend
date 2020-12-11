package com.github.margawron.epidemicalert.users

import com.github.margawron.epidemicalert.exceptions.MessageKeyException
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
        if (userRepository.findByUserName(dto.login) != null) throw MessageKeyException(ValidationException::class, "user.username.exists")
        if (userRepository.findByUserEmail(dto.email) != null) throw MessageKeyException(ValidationException::class, "user.email.exists")

        return userRepository.save(
                User(
                        userName = dto.login,
                        passwordHash = passwordEncoder.encode(dto.password),
                        userEmail = dto.email
                )
        )
    }

    fun findAndCheckUserCredentials(loginDto: LoginDto): User {
        val user: User =  userRepository.findByUserName(loginDto.login) ?: throw MessageKeyException(NoSuchElementException::class, "user.username.not_exists", arrayOf(loginDto.login))

        return if (passwordEncoder.matches(loginDto.password, user.passwordHash)) user else throw MessageKeyException(CredentialException::class, "user.credentials.notValid")
    }

    fun findUserByUsername(username: String?): User {
        if (username == null) throw MessageKeyException(IllegalArgumentException::class, "user.username.not_null")
        return userRepository.findByUserName(username)
                ?: throw MessageKeyException(UsernameNotFoundException::class, "user.username.not_exists", arrayOf(username))
    }
}