package pl.ninthfolder.todo.infrastructure.util

import org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.context.request.WebRequest
import java.util.stream.Collectors


@ControllerAdvice
class ExceptionHandler {

    @ResponseStatus(UNPROCESSABLE_ENTITY)
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationException(exception: MethodArgumentNotValidException, request: WebRequest): ResponseEntity<String> {
        val errors = exception.bindingResult.fieldErrors.stream()
                .map { it.defaultMessage }
                .collect(Collectors.toList()).toString()
        return ResponseEntity.unprocessableEntity().body(errors)
    }

}