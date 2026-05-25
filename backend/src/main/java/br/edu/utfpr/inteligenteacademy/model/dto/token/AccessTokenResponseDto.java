package br.edu.utfpr.inteligenteacademy.model.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(description = "Resposta contendo o access token JWT")
public class AccessTokenResponseDto {

    @Schema(
        description = "JWT utilizado para autenticação do usuário",
        example = "eyJhbGciOiJIUzI1NiJ9..."
    )
    private String accessToken;

    public AccessTokenResponseDto() {
    }

    public AccessTokenResponseDto(String accessToken) {
        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }
}