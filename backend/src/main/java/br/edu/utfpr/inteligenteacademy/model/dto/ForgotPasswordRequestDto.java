package br.edu.utfpr.inteligenteacademy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Data used to request a password reset")
public class ForgotPasswordRequestDto {

    @Schema(
        description = "User email address",
        example = "admin@email.com"
    )
    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be a valid email address")
    private String email;

    public ForgotPasswordRequestDto() {
    }

    public ForgotPasswordRequestDto(String email) {
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}