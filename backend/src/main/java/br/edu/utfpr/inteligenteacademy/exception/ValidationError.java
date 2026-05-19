package br.edu.utfpr.inteligenteacademy.exception;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class ValidationError extends StandardError{
	
	private List<String> messages = new ArrayList<>();
	
	public ValidationError() {}

	public ValidationError(
            LocalDateTime timestamp,
            Integer status,
            String error,
            String message,
            String path
    ) {
        super(timestamp, status, error, message, path);
    }
	
	public List<String> getMessages() {
        return messages;
    }

    public void addMessage(String message) {
        messages.add(message);
    }
}
