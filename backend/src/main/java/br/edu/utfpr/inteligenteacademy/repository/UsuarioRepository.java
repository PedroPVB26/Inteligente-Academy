package br.edu.utfpr.inteligenteacademy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.User;

public interface UsuarioRepository extends JpaRepository<User, Long>{
    boolean existsById(Long id);
	boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<User> findByEmail(String email);
}
