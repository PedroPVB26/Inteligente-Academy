package br.edu.utfpr.inteligenteacademy.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
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

        StandardError error = new StandardError(
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
		
		ValidationError error = new ValidationError(
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
	
	@ExceptionHandler(HttpMessageNotReadableException.class)
	public ResponseEntity<ValidationError> httpMessageNotReadable(
	        HttpMessageNotReadableException e,
	        HttpServletRequest request
	) {

	    ValidationError error = new ValidationError(
                LocalDateTime.now(),
                HttpStatus.BAD_REQUEST.value(),
                "Validation exception",
                "Invalid request body",
                request.getRequestURI()
        );

	    Throwable cause = e.getCause();

	    if (cause != null && cause.getMessage().contains("TipoUsuario")) {
	        error.addMessage("tipoUsuario: must be one of [ALUNO, EDUCADOR, ADMIN]");

	    } else {
	        error.addMessage("Malformed JSON request");
	    }

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(error);
	}
	
	@ExceptionHandler(TokenInvalidException.class)
	public ResponseEntity<StandardError> tokenInvalid(TokenInvalidException e, HttpServletRequest request) {

	    StandardError error = new StandardError(
	            LocalDateTime.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            "Invalid token",
	            e.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
	}
	
	@ExceptionHandler(TokenExpiredException.class)
	public ResponseEntity<StandardError> tokenExpired(TokenExpiredException e, HttpServletRequest request) {

	    StandardError error = new StandardError(
	            LocalDateTime.now(),
	            HttpStatus.GONE.value(), // 410 (bem correto aqui)
	            "Expired token",
	            e.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity.status(HttpStatus.GONE).body(error);
	}
	
	@ExceptionHandler(TokenAlreadyUsedException.class)
	public ResponseEntity<StandardError> tokenUsed(TokenAlreadyUsedException e, HttpServletRequest request) {

	    StandardError error = new StandardError(
	            LocalDateTime.now(),
	            HttpStatus.CONFLICT.value(),
	            "Token already used",
	            e.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity.status(HttpStatus.CONFLICT).body(error);
	}
}
