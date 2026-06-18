package br.edu.utfpr.inteligenteacademy.model.dto.token;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Schema(description = "JWT access token response")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccessTokenResponseDto {

    @Schema(
        description = "JWT access token",
        example = "eyJhbGciOiJIUzI1NiJ9..."
    )
    private String accessToken;
}