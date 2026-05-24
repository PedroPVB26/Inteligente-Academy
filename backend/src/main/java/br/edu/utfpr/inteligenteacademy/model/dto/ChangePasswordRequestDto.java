package br.edu.utfpr.inteligenteacademy.model.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class ChangePasswordRequestDto {

    @NotBlank(message = "currentPassword must not be blank")
    private String currentPassword;

    @NotBlank(message = "newPassword must not be blank")
    @Size(
            min = 8,
            message = "newPassword must contain at least 8 characters"
    )
    private String newPassword;

    @NotBlank(message = "confirmPassword must not be blank")
    private String confirmPassword;

    public ChangePasswordRequestDto() {

    }

    public ChangePasswordRequestDto(
            String currentPassword,
            String newPassword,
            String confirmPassword
    ) {
        this.currentPassword = currentPassword;
        this.newPassword = newPassword;
        this.confirmPassword = confirmPassword;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
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