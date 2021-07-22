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
/*class NotFoundHandler : ResponseEntityExceptionHandler() {
    @ExceptionHandler(value = [(NotFoundException::class)])
    @ResponseStatus(HttpStatus.NOT_FOUND)
    fun NotFoundHandling(exception: NotFoundException): ResponseEntity<ApiError> {
        val apiError = ApiError(HttpStatus.NOT_FOUND, exception.message)
        return ResponseEntity<ApiError>(apiError, HttpStatus.NOT_FOUND)
    }
}*/