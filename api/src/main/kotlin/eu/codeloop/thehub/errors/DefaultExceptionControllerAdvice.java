package eu.codeloop.thehub.errors;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.HttpClientErrorException;

import java.rmi.ServerError;

@ControllerAdvice
@RestController
public class DefaultExceptionControllerAdvice {
    @ExceptionHandler
    ResponseEntity<ServerError> handleException(Exception ex) {
        return new HttpClientErrorException.NotFound(ex.getMessage());
    }

    @ExceptionHandler
    ResponseEntity<ServerError> handleException(GenericError ex) {
        return new HttpClientErrorException.NotFound(ex.getMessage());
    }
}
