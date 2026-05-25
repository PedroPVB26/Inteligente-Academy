package br.edu.utfpr.inteligenteacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.Etiqueta;

public interface EtiquetaRepository extends JpaRepository<Etiqueta, Long>{
    boolean existsById(Integer id);
	boolean existsByNome(String nome);
}
