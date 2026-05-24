package br.edu.utfpr.inteligenteacademy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.PasswordResetToken;
import br.edu.utfpr.inteligenteacademy.entity.Usuario;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String>{
	void deleteByUsuarioAndUsedFalse(Usuario usuario);
	Optional<PasswordResetToken> findByUsuarioAndUsedFalse(Usuario usuario);
}
