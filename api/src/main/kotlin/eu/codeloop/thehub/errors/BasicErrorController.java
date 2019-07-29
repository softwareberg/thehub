package eu.codeloop.thehub.errors;

import org.springframework.boot.autoconfigure.web.servlet.error.AbstractErrorController;
import org.springframework.boot.web.servlet.error.ErrorAttributes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.servlet.http.HttpServletRequest;
import java.rmi.ServerError;
import java.util.Map;

public class BasicErrorController extends AbstractErrorController {
    private static final String PATH = "/error";

    public BasicErrorController(ErrorAttributes errorAttributes) {
        super(errorAttributes);
    }

    @RequestMapping(PATH)
    ResponseEntity<ServerError> error(HttpServletRequest request) {
        Map<String, Object> errorAttributes = getErrorAttributes(request, getTraceParameter(request));

        String path = errorAttributes.getOrDefault("path", "").toString();
        String message = errorAttributes.getOrDefault("message", "").toString();
        HttpStatus status = getStatus(request);

        GenericError error = new GenericError(status.value(), message);
        return new ResponseEntity<>(error, status);
    }

    @Override
    public String getErrorPath() {
        return PATH;
    }
}
