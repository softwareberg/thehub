package eu.codeloop.thehub.errors

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController
import org.springframework.boot.web.servlet.error.ErrorAttributes
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
class BasicErrorController(errorAttributes: ErrorAttributes) : AbstractErrorController(errorAttributes) {

    @RequestMapping(PATH)
    fun error(request: HttpServletRequest): ResponseEntity<ErrorResponse> {
        val errorAttributes = getErrorAttributes(request, getTraceParameter(request))
        val path = errorAttributes.getOrDefault("path","").toString()
        val message = errorAttributes.getOrDefault("message", "").toString()
        val status = getStatus(request)

        val error = when(status) {
            HttpStatus.NOT_FOUND -> ErrorResponse(status.value(), "NF", "$path not found.")
            else -> ErrorResponse(status.value(), "GE", message)
        }

        return ResponseEntity(error, status)
    }

    override fun getErrorPath(): String {
        return PATH
    }

    companion object {
        private const val PATH = "/error"
    }
}
