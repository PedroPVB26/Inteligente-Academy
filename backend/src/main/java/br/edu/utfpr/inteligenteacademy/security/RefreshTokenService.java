package br.edu.utfpr.inteligenteacademy.security;

import br.edu.utfpr.inteligenteacademy.entity.RefreshToken;
import br.edu.utfpr.inteligenteacademy.entity.Usuario;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenExpiredException;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenInvalidException;
import br.edu.utfpr.inteligenteacademy.model.dto.login.LoginResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.token.AccessTokenResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.token.RefreshTokenRequestDto;
import br.edu.utfpr.inteligenteacademy.repository.RefreshTokenRepository;
import br.edu.utfpr.inteligenteacademy.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.Instant;
import java.util.UUID;

@Service
public class RefreshTokenService {
    @Value("${jwt.refresh-token-expiration}")
    private Long refreshTokenExpiration;

    private final RefreshTokenRepository refreshTokenRepository;
    private final UsuarioRepository  usuarioRepository;
    private final JwtService jwtService;

    public RefreshTokenService(
            RefreshTokenRepository refreshTokenRepository,
            UsuarioRepository usuarioRepository,
            JwtService jwtService
    ) {
        this.refreshTokenRepository = refreshTokenRepository;
        this.usuarioRepository = usuarioRepository;
        this.jwtService = jwtService;
    }

    //
    public LoginResponseDto refreshAccessToken(RefreshTokenRequestDto refreshTokenRequestDto) {
        RefreshToken oldToken = refreshTokenRepository
                .findByRefreshToken(refreshTokenRequestDto.getRefreshToken())
                .orElseThrow(() ->
                        new TokenInvalidException("Invalid refresh token."));

        // Token ja foi revogado? Possível ataque
        if (oldToken.isRevoked()) {
            throw new TokenInvalidException("Refresh token already revoked.");
        }

        isTokenExpired(oldToken);

        Usuario usuario = oldToken.getUsuario();

        // Revoga o refreshToken antigo
        oldToken.setRevoked(true);
        refreshTokenRepository.save(oldToken);

        RefreshToken newRefreshToken = generateRefreshToken(usuario);
        String newAccessToken = jwtService.generateAccessToken(usuario);

        return new LoginResponseDto(newAccessToken, newRefreshToken.getRefreshToken());
    }

    public RefreshToken generateRefreshToken(Usuario usuario) {
        RefreshToken refreshToken = new RefreshToken();

        refreshToken.setUsuario(usuario);
        refreshToken.setExpiryDate(Instant.now().plusMillis(refreshTokenExpiration));
        refreshToken.setRefreshToken(UUID.randomUUID().toString());

        return refreshTokenRepository.save(refreshToken);
    }

    public RefreshToken findByToken(String token) {
        return refreshTokenRepository
                .findByRefreshToken(token)
                .orElseThrow(() -> new TokenInvalidException("Invalid refresh token."));
    }

    public void isTokenExpired(RefreshToken token) {
        if (token.getExpiryDate().isBefore(Instant.now())) {
            refreshTokenRepository.delete(token);
            throw new TokenExpiredException("Refresh token expired. Please login again.");
        }
    }

    @Transactional
    public void delete(RefreshToken token) {
        refreshTokenRepository.delete(token);
    }
}
