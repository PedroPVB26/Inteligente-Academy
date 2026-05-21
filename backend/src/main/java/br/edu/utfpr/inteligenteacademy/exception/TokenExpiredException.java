package br.edu.utfpr.inteligenteacademy.exception;
public class TokenExpiredException extends RuntimeException {

    public TokenExpiredException(String message) {
        super(message);
    }
}