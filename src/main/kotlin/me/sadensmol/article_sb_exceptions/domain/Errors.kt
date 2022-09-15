package me.sadensmol.article_sb_exceptions.domain

class BadRequestException(message: String? = null, val code: String? = null) : Exception(message)
class NotFoundException(message: String? = null, val code: String? = null) : Exception(message)
class AlreadyExistsException(message: String? = null, val code: String? = null) : Exception(message)
class TerminalException(message: String? = null, val code: String? = null) : Exception(message)

open class BusinessError(message: String? = null, val code: String? = null) : Exception(message)
data class ErrorResponse(
    val code: String? = null,
    val message: String? = null,
    val details: String? = null,
)
