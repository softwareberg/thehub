package eu.codeloop.thehub.errors

import org.springframework.http.HttpStatus
import org.springframework.http.HttpStatus.BAD_REQUEST
import org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR
import org.springframework.http.HttpStatus.NOT_FOUND
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestController

@ControllerAdvice
@RestController
class DefaultExceptionControllerAdvice {

    @ExceptionHandler
    internal fun handleException(ex: Exception): ResponseEntity<ErrorResponse> {
        return createResponse(INTERNAL_SERVER_ERROR, "GE", "Unknown error")
    }

    @ExceptionHandler
    internal fun handleException(ex: NotFoundExceptions): ResponseEntity<ErrorResponse> {
        return createResponse(NOT_FOUND, "NF", ex.msg)
    }

    @ExceptionHandler
    internal fun handleException(ex: BadRequestExceptions): ResponseEntity<ErrorResponse> {
        return createResponse(BAD_REQUEST, "BR", ex.msg)
    }

    private fun createResponse(status: HttpStatus, code: String, message: String): ResponseEntity<ErrorResponse> {
        val errorResponse = ErrorResponse(status.value(), code, message)
        return ResponseEntity(errorResponse, status)
    }
}
