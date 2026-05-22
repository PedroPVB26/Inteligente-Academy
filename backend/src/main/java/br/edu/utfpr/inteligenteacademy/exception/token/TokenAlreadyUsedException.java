package br.edu.utfpr.inteligenteacademy.exception.token;
public class TokenAlreadyUsedException extends RuntimeException {

	private static final long serialVersionUID = 1L;
	
    public TokenAlreadyUsedException(String message) {
        super(message);
    }
}