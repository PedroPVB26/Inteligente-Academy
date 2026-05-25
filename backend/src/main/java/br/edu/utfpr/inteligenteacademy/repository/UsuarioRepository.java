package br.edu.utfpr.inteligenteacademy.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Long>{
    boolean existsById(Long id);
	boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
    Optional<Usuario> findByEmail(String email);
}
