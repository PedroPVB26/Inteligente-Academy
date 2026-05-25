package br.edu.utfpr.inteligenteacademy.repository;

import br.edu.utfpr.inteligenteacademy.entity.RefreshToken;
import br.edu.utfpr.inteligenteacademy.entity.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long> {
    Optional<RefreshToken> findByRefreshToken(String refreshToken);
    Optional<RefreshToken> findByUsuario(Usuario usuario);
}
