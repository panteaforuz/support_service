package ir.balonet.support_service.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


class ForbiddenException(massage: String) : Exception(massage)

@ControllerAdvice
class ForbiddenHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [(ForbiddenException::class)])
    @ResponseStatus(HttpStatus.FORBIDDEN)
    fun notFoundHandling(exception: ForbiddenException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.FORBIDDEN, exception.message)
        return ResponseEntity<ApiError>(apiError, HttpStatus.FORBIDDEN)
    }
}