package br.edu.utfpr.inteligenteacademy.handler;

import java.time.LocalDateTime;
import java.util.Arrays;


import br.edu.utfpr.inteligenteacademy.model.dto.user.UserRole;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;

import br.edu.utfpr.inteligenteacademy.exception.BadRequestException;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.PasswordChangedException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.exception.StandardError;
import br.edu.utfpr.inteligenteacademy.exception.ValidationError;
import br.edu.utfpr.inteligenteacademy.exception.auth.EmailNotVerifiedException;
import br.edu.utfpr.inteligenteacademy.exception.auth.InvalidCredentialsException;
import br.edu.utfpr.inteligenteacademy.exception.auth.UserDeletedException;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenAlreadyUsedException;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenExpiredException;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenInvalidException;
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

	    if (cause != null && cause.getMessage().contains("UserRole")) {
			error.addMessage(
					"userRole: must be one of " +
							Arrays.toString(UserRole.values())
			);

	    } else if (cause instanceof InvalidFormatException invalidFormat) {

	        String fieldName =
	            invalidFormat.getPath().getFirst().getFieldName();

	        error.addMessage(fieldName + ": invalid value type");
	    }else {
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
	
	@ExceptionHandler(InvalidCredentialsException.class)
	public ResponseEntity<StandardError> invalidCredentials(
	        InvalidCredentialsException e,
	        HttpServletRequest request
	) {

	    StandardError error = new StandardError(
	            LocalDateTime.now(),
	            HttpStatus.UNAUTHORIZED.value(),
	            "Invalid credentials",
	            e.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity
	            .status(HttpStatus.UNAUTHORIZED)
	            .body(error);
	}
	
	@ExceptionHandler(EmailNotVerifiedException.class)
	public ResponseEntity<StandardError> emailNotVerified(
	        EmailNotVerifiedException e,
	        HttpServletRequest request
	) {

	    StandardError error = new StandardError(
	            LocalDateTime.now(),
	            HttpStatus.FORBIDDEN.value(),
	            "Email not verified",
	            e.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity
	            .status(HttpStatus.FORBIDDEN)
	            .body(error);
	}
	
	@ExceptionHandler(UserDeletedException.class)
	public ResponseEntity<StandardError> userDeleted(
	        UserDeletedException e,
	        HttpServletRequest request
	) {

	    StandardError error = new StandardError(
	            LocalDateTime.now(),
	            HttpStatus.FORBIDDEN.value(),
	            "User deleted",
	            e.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity
	            .status(HttpStatus.FORBIDDEN)
	            .body(error);
	}
	
	@ExceptionHandler(PasswordChangedException.class)
	public ResponseEntity<StandardError> handlePasswordChangedException(
	        PasswordChangedException ex,
	        HttpServletRequest request
	) {

	    StandardError error = new StandardError(
	            LocalDateTime.now(),
	            HttpStatus.UNAUTHORIZED.value(),
	            "Password changed",
	            ex.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity
	            .status(HttpStatus.UNAUTHORIZED)
	            .body(error);
	}
	
	@ExceptionHandler(BadRequestException.class)
	public ResponseEntity<StandardError> badRequest(
	        BadRequestException e,
	        HttpServletRequest request
	) {

	    StandardError error = new StandardError(
	            LocalDateTime.now(),
	            HttpStatus.BAD_REQUEST.value(),
	            "Bad request",
	            e.getMessage(),
	            request.getRequestURI()
	    );

	    return ResponseEntity
	            .status(HttpStatus.BAD_REQUEST)
	            .body(error);
	}
}
