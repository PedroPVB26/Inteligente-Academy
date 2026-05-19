package br.edu.utfpr.inteligenteacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.Usuario;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer>{
    boolean existsById(Integer id);
	boolean existsByCpf(String cpf);
    boolean existsByEmail(String email);
}
