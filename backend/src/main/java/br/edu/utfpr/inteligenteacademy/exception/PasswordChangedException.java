package br.edu.utfpr.inteligenteacademy.exception;
public class PasswordChangedException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public PasswordChangedException(String message) {
        super(message);
    }
}