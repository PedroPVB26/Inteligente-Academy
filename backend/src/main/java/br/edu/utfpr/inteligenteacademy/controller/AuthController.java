package br.edu.utfpr.inteligenteacademy.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.inteligenteacademy.exception.StandardError;
import br.edu.utfpr.inteligenteacademy.model.dto.ForgotPasswordRequestDto;
import br.edu.utfpr.inteligenteacademy.model.dto.ResetPasswordRequestDto;
import br.edu.utfpr.inteligenteacademy.model.dto.login.LoginRequestDto;
import br.edu.utfpr.inteligenteacademy.model.dto.login.LoginResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioResponseDto;
import br.edu.utfpr.inteligenteacademy.service.AuthService;
import br.edu.utfpr.inteligenteacademy.service.PasswordService;
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
    private final PasswordService passwordResetService;
    
    public AuthController(AuthService authService, PasswordService passwordResetService) {
        this.authService = authService;
        this.passwordResetService = passwordResetService;
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
    	System.out.println("➡️ CHEGOU NO CONTROLLER");
        UsuarioResponseDto response = authService.register(dto);
        System.out.println("⬅️ SAIU DO SERVICE");
        return ResponseEntity.ok(response);
    }
    


    @GetMapping("/verify-email")
    public ResponseEntity<String> verificarEmail(@RequestParam String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok("Email verificado com sucesso!");
    }
    
    @PostMapping("/forgot-password")
    public ResponseEntity<Void> forgotPassword(@RequestBody ForgotPasswordRequestDto request) {
        passwordResetService.requestReset(request.getEmail());
        return ResponseEntity.ok().build(); // sempre 200, mesmo se e-mail não existir
    }
    
    @PostMapping("/reset-password")
    public ResponseEntity<Void> resetPassword(@Valid @RequestBody ResetPasswordRequestDto request) {
        passwordResetService.resetPassword(request);
        return ResponseEntity.noContent().build(); // 204
    }
    
}
