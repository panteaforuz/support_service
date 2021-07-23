package ir.balonet.support_service.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


class NotFoundException(massage: String) : Exception(massage)

@ControllerAdvice
class NotFoundHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [(NotFoundException::class)])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun notFoundHandling(exception: NotFoundException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.NOT_FOUND, exception.message)
        return ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND)
    }
}