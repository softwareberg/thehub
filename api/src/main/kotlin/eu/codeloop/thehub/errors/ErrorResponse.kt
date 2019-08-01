package eu.codeloop.thehub.errors

import org.slf4j.MDC

data class ErrorResponse(val status: Int, val code: String, val message: String) {

    val id: String? get() = MDC.get("spanId")
}
