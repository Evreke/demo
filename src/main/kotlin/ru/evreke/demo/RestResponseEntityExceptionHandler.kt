package ru.evreke.demo

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import ru.evreke.demo.exceptions.AlreadyPayedException
import ru.evreke.demo.exceptions.BookingException
import ru.evreke.demo.exceptions.NotFoundException

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(value = [NotFoundException::class, BookingException::class, AlreadyPayedException::class])
    fun handleCustomExceptions(ex: Exception, request: WebRequest): ResponseEntity<Any> {
        val responseBody = ex.message
        return handleExceptionInternal(ex, responseBody, HttpHeaders(), HttpStatus.NOT_FOUND, request)
    }
}