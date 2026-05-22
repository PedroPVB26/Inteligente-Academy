package br.edu.utfpr.inteligenteacademy.model.dto.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

public class LoginRequestDto {
    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be a valid email address")
    private String email;
    
    @NotBlank(message = "senha must not be blank")
    private String senha;

	public LoginRequestDto(String email, String senha) {
		this.email = email;
		this.senha = senha;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getSenha() {
		return senha;
	}

	public void setSenha(String senha) {
		this.senha = senha;
	}
    
	
}
