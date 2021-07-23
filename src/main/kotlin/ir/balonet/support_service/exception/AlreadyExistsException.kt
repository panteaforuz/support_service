package ir.balonet.support_service.exception

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseBody
import org.springframework.web.bind.annotation.ResponseStatus

class AlreadyExistException(massage: String?) : Exception(massage)

@ControllerAdvice
 class AlreadyExistExceptionHandler {
    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(value = [(AlreadyExistException::class)])
    fun AlreadyExistExceptionHandling(exception: AlreadyExistException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.CONFLICT, exception.message)
        return ResponseEntity(apiError, HttpStatus.CONFLICT)
    }
}
