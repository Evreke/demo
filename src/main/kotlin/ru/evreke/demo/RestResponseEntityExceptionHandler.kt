package ru.evreke.demo

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.evreke.demo.exceptions.AlreadyPayedException
import ru.evreke.demo.exceptions.NotFoundException

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [NotFoundException::class])
    fun handleNotFound(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val responseBody = ex.message
        return handleExceptionInternal(ex, responseBody, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }

    @ExceptionHandler(value = [AlreadyPayedException::class])
    fun handleAlreadyPayed(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val responseBody = ex.message
        return handleExceptionInternal(ex, responseBody, HttpHeaders(), HttpStatus.UNPROCESSABLE_ENTITY, request)
    }
}