package br.edu.utfpr.inteligenteacademy.model.dto.password;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Data used to request a password reset")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ForgotPasswordRequestDto {

    @Schema(
        description = "User email address",
        example = "admin@email.com"
    )
    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be a valid email address")
    private String email;
}