package br.edu.utfpr.inteligenteacademy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.TokenVerificacaoEmail;

public interface TokenVerificacaoEmailRepository extends JpaRepository<TokenVerificacaoEmail, Long>{
	Optional<TokenVerificacaoEmail> findByToken(String token);
}
