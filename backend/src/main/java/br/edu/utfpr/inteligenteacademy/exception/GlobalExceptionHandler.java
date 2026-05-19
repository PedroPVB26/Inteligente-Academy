package br.edu.utfpr.inteligenteacademy.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import jakarta.servlet.http.HttpServletRequest;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<StandardError>
    resourceNotFound(ResourceNotFoundException e, HttpServletRequest request) {

        StandardError error =
                new StandardError(
                        LocalDateTime.now(),
                        HttpStatus.NOT_FOUND.value(),
                        "Resource not found",
                        e.getMessage(),
                        request.getRequestURI()
                );

        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(error);
    }
	
	@ExceptionHandler(DatabaseException.class)
	public ResponseEntity<StandardError> databaseException(DatabaseException e, HttpServletRequest request) {
		
		StandardError error = new StandardError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Database exception",
                e.getMessage(),
                request.getRequestURI()
        );
		
		return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(error);
	}

	@ExceptionHandler(MethodArgumentNotValidException.class)
	public ResponseEntity<ValidationError> validationException(MethodArgumentNotValidException e, HttpServletRequest request){
		
		ValidationError error =
	            new ValidationError(
	                    LocalDateTime.now(),
	                    HttpStatus.BAD_REQUEST.value(),
	                    "Validation exception",
	                    "Validation error",
	                    request.getRequestURI()
	            );
		
		for (FieldError fieldError : e.getBindingResult().getFieldErrors()) {

	        error.addMessage(
	                fieldError.getField()
	                + ": "
	                + fieldError.getDefaultMessage()
	        );
	    }
		
		return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(error);
	}
}
