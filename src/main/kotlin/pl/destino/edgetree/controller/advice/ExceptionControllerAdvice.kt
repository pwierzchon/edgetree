package pl.destino.edgetree.controller.advice

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import pl.destino.edgetree.api.model.ExceptionMessage
import pl.destino.edgetree.exception.DuplicateEdgeException
import pl.destino.edgetree.exception.EdgeNotFoundException
import pl.destino.edgetree.exception.RootNotFoundException

@ControllerAdvice
class ExceptionControllerAdvice {

    @ExceptionHandler
    fun handleDuplicateEdgeException(ex: DuplicateEdgeException): ResponseEntity<ExceptionMessage> {
        return getErrorResponse(ex.message, HttpStatus.FORBIDDEN)
    }

    @ExceptionHandler
    fun handleEdgeNotFoundException(ex: EdgeNotFoundException): ResponseEntity<ExceptionMessage> {
        return getErrorResponse(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleRootNotFoundException(ex: RootNotFoundException): ResponseEntity<ExceptionMessage> {
        return getErrorResponse(ex.message, HttpStatus.NOT_FOUND)
    }

    @ExceptionHandler
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<ExceptionMessage> {
        return getErrorResponse(ex.message, HttpStatus.BAD_REQUEST)
    }

    private fun getErrorResponse(message: String?, code: HttpStatus): ResponseEntity<ExceptionMessage> {
        val errorMessage = ExceptionMessage(
            code.value(),
            message
        )
        return ResponseEntity(errorMessage, code)
    }
}