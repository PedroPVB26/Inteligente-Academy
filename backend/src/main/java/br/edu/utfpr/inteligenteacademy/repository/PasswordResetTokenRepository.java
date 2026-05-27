package br.edu.utfpr.inteligenteacademy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.PasswordResetToken;
import br.edu.utfpr.inteligenteacademy.entity.User;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, String>{
	void deleteByUserAndUsedFalse(User user);
	Optional<PasswordResetToken> findByUserAndUsedFalse(User user);
}
