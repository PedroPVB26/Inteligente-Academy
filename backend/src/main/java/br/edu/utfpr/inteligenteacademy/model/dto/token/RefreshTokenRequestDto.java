package br.edu.utfpr.inteligenteacademy.model.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "Dados necessários para renovação do access token")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RefreshTokenRequestDto {

    @Schema(
        description = "Refresh token válido do usuário",
        example = "550e8400-e29b-41d4-a716-446655440000"
    )
    @NotBlank(message = "refreshToken must not be blank")
    private String refreshToken;
}