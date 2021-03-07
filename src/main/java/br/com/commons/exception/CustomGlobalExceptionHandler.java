package br.com.commons.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.commons.CustomErrorResponse;

@ControllerAdvice
@RestController
public class CustomGlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(ApplicationNotFoundException.class)
    //public void springHandleNotFound(HttpServletResponse response) throws IOException {
    public ResponseEntity<CustomErrorResponse> customHandleNotFound(Exception ex, WebRequest request) {
    	//response.sendError(HttpStatus.NOT_FOUND.value());
        CustomErrorResponse errors = new CustomErrorResponse();
        errors.setError(ex.getMessage());
        errors.setStatus(HttpStatus.NOT_FOUND.value());

        return new ResponseEntity<>(errors, HttpStatus.NOT_FOUND);
    }

}
