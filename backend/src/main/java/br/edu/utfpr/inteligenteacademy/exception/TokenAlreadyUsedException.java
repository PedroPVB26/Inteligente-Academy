package br.edu.utfpr.inteligenteacademy.exception;
public class TokenAlreadyUsedException extends RuntimeException {

    public TokenAlreadyUsedException(String message) {
        super(message);
    }
}