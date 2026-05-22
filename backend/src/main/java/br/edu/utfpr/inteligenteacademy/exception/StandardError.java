package br.edu.utfpr.inteligenteacademy.exception;

import java.time.LocalDateTime;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta padrão de erro da API")
public class StandardError {

    @Schema(
            description = "Data e hora do erro",
            example = "2026-05-22T16:54:04.3721313"
    )
    private LocalDateTime timestamp;

    @Schema(
            description = "Código HTTP",
            example = "401"
    )
    private Integer status;

    @Schema(
            description = "Tipo do erro",
            example = "Invalid credentials"
    )
    private String error;

    @Schema(
            description = "Mensagem detalhada",
            example = "Invalid email or password"
    )
    private String message;

    @Schema(
            description = "Endpoint da requisição",
            example = "/auth/login"
    )
    private String path;

    public StandardError() {

    }

    public StandardError(
            LocalDateTime timestamp,
            Integer status,
            String error,
            String message,
            String path
    ) {
        this.timestamp = timestamp;
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
