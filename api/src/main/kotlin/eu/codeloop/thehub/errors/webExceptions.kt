package eu.codeloop.thehub.errors

abstract class WebExceptions(msg: String, cause: Throwable? = null) : RuntimeException(msg, cause)
class NotFoundExceptions(val msg: String, cause: Throwable? = null) : WebExceptions(msg, cause)
class BadRequestExceptions(val msg: String, cause: Throwable? = null) : WebExceptions(msg, cause)
