package com.github.margawron.epidemicalert.validation

import com.github.margawron.epidemicalert.exceptions.MessageKeyException
import org.slf4j.LoggerFactory
import org.springframework.context.MessageSource
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.FieldError
import org.springframework.web.HttpRequestMethodNotSupportedException
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.util.*
import javax.security.auth.login.CredentialException
import javax.validation.ConstraintViolationException
import javax.validation.ValidationException

@ControllerAdvice
class ControllerExceptionHandler(private val messageSource: MessageSource) {

    companion object{
        val log = LoggerFactory.getLogger(ControllerExceptionHandler::class.java)
    }

    @ExceptionHandler(value = [HttpRequestMethodNotSupportedException::class])
    fun handleHttpMethodNotSupportedException(e: HttpRequestMethodNotSupportedException): ResponseEntity<ErrorDto> {
        log.info("handleHttpMethodNotSupportedException", e)
        return ResponseEntity.badRequest().body(ErrorDto(e.javaClass.simpleName, "http method", e.localizedMessage))
    }

    @ExceptionHandler(value = [HttpMessageNotReadableException::class])
    fun handleHttpMessageNotReadableException(e: HttpMessageNotReadableException): ResponseEntity<ErrorDto> {
        log.info("handleHttpMessageNotReadableException", e)
        return ResponseEntity.badRequest().body(ErrorDto(e.javaClass.simpleName, "body", "Invalid request"))
    }

    @ExceptionHandler(value = [MessageKeyException::class])
    fun handleTranslatedException(e: MessageKeyException): ResponseEntity<ErrorDto> {
        log.info("handleTranslateException", e)
        return ResponseEntity.badRequest().body(ErrorDto(e.originClass, null, messageSource.getMessage(e.messageKey, e.args, Locale("pl"))))
    }

    @ExceptionHandler(value = [MethodArgumentNotValidException::class])
    fun handleMethodArgumentNotValid(e: MethodArgumentNotValidException): ResponseEntity<List<ErrorDto>> {
        log.info("handleMethodArgumentNotValid", e)
        val errors = e.bindingResult.allErrors.map {
            val erroredObject = "Field: ${(it as FieldError).field}"
            ErrorDto(MethodArgumentNotValidException::class.simpleName, erroredObject, it.defaultMessage)
        }
        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(value = [ConstraintViolationException::class])
    fun handleConstraintViolationException(e: ConstraintViolationException): ResponseEntity<List<ErrorDto>> {
        log.info("handleConstraintViolationException", e)
        val errors = e.constraintViolations.map {
            val message = it.message
            val erroredObject = "Field: ${it.propertyPath}, Invalid value: ${it.invalidValue}"
            ErrorDto(ConstraintViolationException::class.simpleName, erroredObject, message)
        }
        return ResponseEntity.badRequest().body(errors)
    }

    @ExceptionHandler(value = [ValidationException::class])
    fun handleValidationException(e: ValidationException): ResponseEntity<ErrorDto> {
        log.info("handleValidationException", e)
        return ResponseEntity.badRequest().body(ErrorDto(ValidationException::class.simpleName, null, e.message))
    }
    @ExceptionHandler(value = [NoSuchElementException::class])
    fun handleNoSuchElementException(e: NoSuchElementException): ResponseEntity<ErrorDto> {
        log.info("handleNoSuchElementException", e)
        return ResponseEntity(ErrorDto(NoSuchElementException::class.simpleName, null, e.message), HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler(value = [CredentialException::class])
    fun handleCredentialException(e: CredentialException): ResponseEntity<ErrorDto> {
        log.info("handleCredentialException", e)
        return ResponseEntity.badRequest().body(ErrorDto(CredentialException::class.simpleName, null, e.message))
    }
}