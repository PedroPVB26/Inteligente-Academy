package br.edu.utfpr.inteligenteacademy.model.dto.login;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;

@Schema(description = "Credenciais utilizadas para autenticação")
public class LoginRequestDto {

    @Schema(
            description = "Email do usuário",
            example = "admin@email.com"
    )
    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be a valid email address")
    private String email;

    @Schema(
            description = "Senha do usuário",
            example = "123456789"
    )
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
