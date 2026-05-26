package br.edu.utfpr.inteligenteacademy.model.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;

public class LoginResponseDto {

	@Schema(
			description = "JWT access token",
			example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
	)
	private String accessToken;

	@Schema(
			description = "Refresh token used to generate a new access token",
			example = "dGhpc0lzUmVmcmVzaFRva2Vu"
	)
	private String refreshToken;

	public LoginResponseDto(String accessToken, String refreshToken) {
		this.accessToken = accessToken;
		this.refreshToken = refreshToken;
	}

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}
}