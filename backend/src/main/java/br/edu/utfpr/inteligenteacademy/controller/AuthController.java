package br.edu.utfpr.inteligenteacademy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.inteligenteacademy.exception.StandardError;
import br.edu.utfpr.inteligenteacademy.model.dto.login.LoginRequestDto;
import br.edu.utfpr.inteligenteacademy.model.dto.login.LoginResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioResponseDto;
import br.edu.utfpr.inteligenteacademy.service.AuthService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/auth")
@Tag(name = "Autenticação", description = "Para logar, cadastrar e verificar email")
public class AuthController {
    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }
    
    @Operation(
        summary = "Login",
        description = "Realiza o login do usuário e retorna um JWT token de autenticação. A duração do token é de 1 hora. >>> AJUSTAR ISSO <<<"
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Login realizado com sucesso",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))
            ),

            @ApiResponse(
                responseCode = "401",
                description = "Email ou senha inválidos",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))
            )
        }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "Credenciais do usuário",
            required = true,
            content = @Content(
                schema = @Schema(implementation = LoginRequestDto.class)
            )
        )

        @Valid @RequestBody LoginRequestDto loginRequestDto
    ){
    	return ResponseEntity.ok(authService.login(loginRequestDto));
    }
    


    @PostMapping("/register")
    public ResponseEntity<UsuarioResponseDto> register(@Valid @RequestBody UsuarioCreationDto dto) {

        UsuarioResponseDto response = authService.register(dto);

        return ResponseEntity.ok(response);
    }
    


    @GetMapping("/verificar-email")
    public ResponseEntity<String> verificarEmail(@RequestParam String token) {

        authService.verifyEmail(token);

        return ResponseEntity.ok("Email verificado com sucesso!");
    }
}
