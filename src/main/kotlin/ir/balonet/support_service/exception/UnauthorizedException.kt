package ir.balonet.support_service.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler

class UnauthorizedException(massage: String) : Exception(massage)


@ControllerAdvice
class UnauthorizedExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [(UnauthorizedException::class)])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun unauthorizedHandling(exception: UnauthorizedException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.UNAUTHORIZED , exception.message)
        return ResponseEntity<ApiError>(apiError, HttpStatus.UNAUTHORIZED)
    }
}