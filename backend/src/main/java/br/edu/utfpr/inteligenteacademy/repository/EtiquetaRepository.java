package br.edu.utfpr.inteligenteacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.Tag;

public interface EtiquetaRepository extends JpaRepository<Tag, Long>{
    boolean existsById(Integer id);
	boolean existsByName(String name);
}
