package ir.balonet.support_service.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler


class PreconditionException(massage: String) : Exception(massage)


@ControllerAdvice
class PreconditionExceptionHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [(PreconditionException::class)])
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun preconditionHandling(exception: PreconditionException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.PRECONDITION_FAILED , exception.message)
        return ResponseEntity<ApiError>(apiError, HttpStatus.PRECONDITION_FAILED)
    }
}