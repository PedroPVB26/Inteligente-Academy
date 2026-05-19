package br.edu.utfpr.inteligenteacademy.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(
            String message
    ) {
        super(message);
    }
}