package br.edu.utfpr.inteligenteacademy.exception.auth;
public class UserDeletedException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public UserDeletedException(String message) {
        super(message);
    }
}