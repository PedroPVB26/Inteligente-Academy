package br.edu.utfpr.inteligenteacademy.model.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Dados necessários para renovação do access token")
public class RefreshTokenRequestDto {

    @Schema(
        description = "Refresh token válido do usuário",
        example = "550e8400-e29b-41d4-a716-446655440000"
    )
    @NotBlank(message = "refreshToken must not be blank")
    private String refreshToken;

    public RefreshTokenRequestDto() {
    }

    public RefreshTokenRequestDto(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}