package com.github.margawron.epidemicalert.users

import com.github.margawron.epidemicalert.exceptions.ErrorCodeException
import com.github.margawron.epidemicalert.exceptions.ErrorCodeWithFieldException
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import javax.security.auth.login.CredentialException
import javax.validation.ValidationException
import kotlin.NoSuchElementException

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    @Transactional
    fun createAndSaveAccount(dto: RegistrationDto): User {
        if (userRepository.findByUserName(dto.login) != null) throw ErrorCodeWithFieldException(
            ValidationException::class,
            "user.username.exists",
            null,
            "login"
        )
        if (userRepository.findByUserEmail(dto.email) != null) throw ErrorCodeWithFieldException(
            ValidationException::class,
            "user.email.exists",
            null,
            "email"
        )

        return userRepository.save(
            User(
                userName = dto.login,
                passwordHash = passwordEncoder.encode(dto.password),
                userEmail = dto.email
            )
        )
    }

    fun findAndCheckUserCredentials(loginDto: LoginDto): User {
        val user: User = userRepository.findByUserName(loginDto.login) ?: throw ErrorCodeWithFieldException(
            loginDto::class,
            "user.username.not_exists",
            arrayOf(loginDto.login),
            "login"
        )

        return if (passwordEncoder.matches(
                loginDto.password,
                user.passwordHash
            )
        ) user else throw ErrorCodeWithFieldException(
            CredentialException::class,
            "user.credentials.notValid",
            null,
            "password"
        )
    }

    fun findUserById(id: Long) = userRepository.findById(id).orElse(null) ?: throw ErrorCodeException(User::class, "user.given_id_does_not_exist")

    @Transactional
    fun saveUser(user: User): User = userRepository.save(user)

    fun findUserByUsername(username: String?): User {
        if (username == null) throw ErrorCodeException(IllegalArgumentException::class, "user.username.not_null")
        return userRepository.findByUserName(username)
            ?: throw ErrorCodeException(UsernameNotFoundException::class, "user.username.not_exists", arrayOf(username))
    }

    fun userFromAuth(auth: Authentication): User {
        val userPrincipal = auth.principal as UserPrincipal
        return userRepository.getOne(userPrincipal.id)
    }

    fun getUserById(userId: Long): User = userRepository.findById(userId).orElse(null) ?: throw ErrorCodeException(
        this::class,
        "user.given_id_does_not_exist"
    )

    fun getUsersWithStringInName(string: String): List<User> =
        userRepository.findAllByUserNameContainingIgnoreCase(string)

}