package net.ddns.jaronsky.debter.rest.controller

import net.ddns.jaronsky.debter.rest.service.Log
import net.ddns.jaronsky.debter.rest.service.UnauthorizedActionException
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

/**
 * Created by Wojciech Jaronski
 */
@ControllerAdvice
class ErrorResolverController : ResponseEntityExceptionHandler() {

    companion object : Log();

    @ExceptionHandler(value = [(UnauthorizedActionException::class)])
    fun handleConflict(exception: UnauthorizedActionException, request: WebRequest): ResponseEntity<Any> {
        logger.warn(exception)
        return handleExceptionInternal(exception, exception.message, HttpHeaders(), HttpStatus.BAD_REQUEST, request)
    }
}

