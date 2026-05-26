package br.edu.utfpr.inteligenteacademy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.EmailVerificationToken;

public interface TokenVerificacaoEmailRepository extends JpaRepository<EmailVerificationToken, Long>{
	Optional<EmailVerificationToken> findByToken(String token);
}
