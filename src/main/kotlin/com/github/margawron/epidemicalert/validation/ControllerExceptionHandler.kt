package com.github.margawron.epidemicalert.validation

import com.github.margawron.epidemicalert.exceptions.MessageKeyException
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*
import javax.security.auth.login.CredentialException
import javax.validation.ConstraintViolationException
import javax.validation.ValidationException

@ControllerAdvice
class ControllerExceptionHandler(private val messageSource: MessageSource) {

    @ExceptionHandler(value = [MessageKeyException::class])
    fun handleTranslatedException(e: MessageKeyException): ResponseEntity<ErrorDto> =
            ResponseEntity.badRequest().body(ErrorDto(e.originClass, null, messageSource.getMessage(e.messageKey, e.args, Locale.getDefault())))

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValid(ex: MethodArgumentNotValidException): ResponseEntity<List<ErrorDto>> {
        val errors = ex.bindingResult.allErrors.map {
            val erroredObject = "Field: ${(it as FieldError).field}"
            ErrorDto(MethodArgumentNotValidException::class.simpleName, erroredObject, it.defaultMessage)
        }
        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<List<ErrorDto>> {
        val errors = e.constraintViolations.map {
            val message = it.message
            val erroredObject = "Field: ${it.propertyPath}, Invalid value: ${it.invalidValue}"
            ErrorDto(ConstraintViolationException::class.simpleName, erroredObject, message)
        }
        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(value = [ValidationException::class])
    fun handleValidationException(e: ValidationException): ResponseEntity<ErrorDto> =
            ResponseEntity.badRequest().body(ErrorDto(ValidationException::class.simpleName, null, e.message))

    @ExceptionHandler(value = [NoSuchElementException::class])
    fun handleNoSuchElementException(e: NoSuchElementException): ResponseEntity<ErrorDto> =
            ResponseEntity(ErrorDto(NoSuchElementException::class.simpleName, null, e.message), HttpStatus.NOT_FOUND)

    @ExceptionHandler(value = [CredentialException::class])
    fun handleCredentialException(e: CredentialException): ResponseEntity<ErrorDto> =
            ResponseEntity.badRequest().body(ErrorDto(CredentialException::class.simpleName, null, e.message))

}