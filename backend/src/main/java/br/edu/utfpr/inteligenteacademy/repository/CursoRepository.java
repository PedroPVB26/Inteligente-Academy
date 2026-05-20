package br.edu.utfpr.inteligenteacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.Curso;

public interface CursoRepository extends JpaRepository<Curso, Integer>{
    boolean existsById(Integer id);
	boolean existsByNome(String nome);
    boolean existsByDescricao(String descricao);
	boolean existsByDuracao(Integer duracao);
}
