package me.sadensmol.article_sb_exceptions.controller

import me.sadensmol.article_sb_exceptions.domain.AlreadyExistsException
import me.sadensmol.article_sb_exceptions.domain.BadRequestException
import me.sadensmol.article_sb_exceptions.domain.ErrorResponse
import me.sadensmol.article_sb_exceptions.domain.NotFoundException
import me.sadensmol.article_sb_exceptions.domain.TerminalException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import org.springframework.web.bind.support.WebExchangeBindException

@RestControllerAdvice
class ControllerAdvice {
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(WebExchangeBindException::class)
    fun handleValidations(ex: WebExchangeBindException): ErrorResponse {
        val errors = ex.bindingResult
            .allErrors.mapNotNull { it.defaultMessage }.joinToString("\n")
        return ErrorResponse("validation_error", errors)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(BadRequestException::class)
    fun handleBadRequestException(ex: BadRequestException) = ErrorResponse(message = ex.message, code = ex.code)

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(NotFoundException::class)
    fun handleNotFoundException(ex: NotFoundException) = ErrorResponse(message = ex.message, code = ex.code)

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(AlreadyExistsException::class)
    fun handleAlreadyExistsException(ex: AlreadyExistsException) = ErrorResponse(message = ex.message, code = ex.code)

    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(TerminalException::class)
    fun handleTerminalException(ex: TerminalException) = ErrorResponse(message = ex.message, code = ex.code)
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    @ExceptionHandler(Exception::class)
    fun handleUncaughtException(ex: Exception) =
        ErrorResponse(
            message = "Some error occurred", code = "internal_error",
            details = ex.message
        )

}

