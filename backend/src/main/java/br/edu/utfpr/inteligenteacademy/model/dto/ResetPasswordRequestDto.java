package br.edu.utfpr.inteligenteacademy.model.dto;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

@Schema(description = "Password reset request")
public class ResetPasswordRequestDto {

    @Schema(
            description = "User email address",
            example = "admin@email.com"
    )
    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be a valid email address")
    private String email;

    @Schema(
            description = "Password reset token",
            example = "550e8400-e29b-41d4-a716-446655440000"
    )
    @NotBlank(message = "token must not be blank")
    private String token;

    @Schema(
            description = "New password",
            example = "12345678"
    )
    @NotBlank(message = "newPassword must not be blank")
    @Size(
            min = 8,
            message = "newPassword must contain at least 8 characters"
    )
    private String newPassword;

    @Schema(
            description = "Password confirmation",
            example = "12345678"
    )
    @NotBlank(message = "confirmPassword must not be blank")
    private String confirmPassword;

    public ResetPasswordRequestDto() {
    }

    public ResetPasswordRequestDto(
            String email,
            String token,
            String newPassword,
            String confirmPassword
    ) {
        this.email = email;
        this.token = token;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
}