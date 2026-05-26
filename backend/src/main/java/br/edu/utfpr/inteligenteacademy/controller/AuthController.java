package br.edu.utfpr.inteligenteacademy.controller;

import br.edu.utfpr.inteligenteacademy.model.dto.token.RefreshTokenRequestDto;
import br.edu.utfpr.inteligenteacademy.security.RefreshTokenService;
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
import br.edu.utfpr.inteligenteacademy.model.dto.user.UserCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.user.UserResponseDto;
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
@Tag(
        name = "Authentication",
        description = "Endpoints for login, registration, email verification, password recovery, and token refresh"
)
public class AuthController {
    private final AuthService authService;
    private final RefreshTokenService  refreshTokenService;
    private final PasswordService passwordResetService;
    
    public AuthController(
            AuthService authService,
            PasswordService passwordResetService,
            RefreshTokenService refreshTokenService
    ) {
        this.authService = authService;
        this.passwordResetService = passwordResetService;
        this.refreshTokenService = refreshTokenService;
    }
    
    @Operation(
        summary = "User login",
        description = "Authenticates the user and returns a JWT access token and refresh token"
    )
    @ApiResponses(value = {
            @ApiResponse(
                responseCode = "200",
                description = "Login successful",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = LoginResponseDto.class))
            ),

            @ApiResponse(
                responseCode = "401",
                description = "Invalid email or password",
                content = @Content(mediaType = "application/json", schema = @Schema(implementation = StandardError.class))
            )
        }
    )
    @PostMapping("/login")
    public ResponseEntity<LoginResponseDto> login(
        @io.swagger.v3.oas.annotations.parameters.RequestBody(
            description = "User authentication credentialso",
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
    public ResponseEntity<UserResponseDto> register(@Valid @RequestBody UserCreationDto dto) {
        UserResponseDto response = authService.register(dto);
        return ResponseEntity.ok(response);
    }
    


    @GetMapping("/verify-email")
    public ResponseEntity<String> verificarEmail(@RequestParam String token) {
        authService.verifyEmail(token);
        return ResponseEntity.ok("Email successfully verified");
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


    @PostMapping("/refresh")
    public ResponseEntity<LoginResponseDto> refreshToken(@Valid @RequestBody RefreshTokenRequestDto request) {
        LoginResponseDto response = refreshTokenService.refreshAccessToken(request);
        return ResponseEntity.ok(response);
    }


    @PostMapping("/logout")
    public ResponseEntity<Void> logout(
            @Valid @RequestBody RefreshTokenRequestDto request) {

        authService.logout(request.getRefreshToken());

        return ResponseEntity.noContent().build();
    }
}
