package eu.codeloop.thehub.errors

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class BasicErrorController(errorAttributes: ErrorAttributes) : AbstractErrorController(errorAttributes) {

    @RequestMapping(PATH)
    internal fun error(request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorAttributes = getErrorAttributes(request, getTraceParameter(request))
        val message = errorAttributes.getOrDefault("message", "").toString()
        val status = getStatus(request)

        val error = ErrorResponse(status.value(), "GE", message)
        return ResponseEntity(error, status)
    }

    override fun getErrorPath(): String {
        return PATH
    }

    companion object {
        private const val PATH = "/error"
    }
}
