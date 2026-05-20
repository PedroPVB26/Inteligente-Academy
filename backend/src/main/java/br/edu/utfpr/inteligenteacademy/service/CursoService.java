package br.edu.utfpr.inteligenteacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.Curso;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.dto.CursoCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.CursoResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.CursoRepository;


@Service
public class CursoService {
	private final CursoRepository cursoRepository;
	
	public CursoService(CursoRepository cursoRepository) {
		this.cursoRepository = cursoRepository;
	}
	
	@Transactional(readOnly = true)
	public List<CursoResponseDto> findAll(){
		List<Curso> Cursos = cursoRepository.findAll();
		return Cursos.stream().map(x -> new CursoResponseDto(x)).toList();
	}
	
	
	@Transactional(readOnly = true)
	public CursoResponseDto findById(Integer CursoId) {
		Curso Curso =
		        cursoRepository.findById(CursoId)
		        .orElseThrow(() ->
			        new ResourceNotFoundException(
			                "Curso with id "
			                + CursoId
			                + " not found"
			        )
		        );
		return new CursoResponseDto(Curso);
	}
	
	@Transactional
	public CursoResponseDto save(CursoCreationDto CursoCreationDto) {

		if(cursoRepository.existsByNome(CursoCreationDto.getNome())) {
			throw new DatabaseException("Nome already exists in the database");
		}
		
		Curso curso = new Curso(CursoCreationDto);
		
		Curso CursoSalvo = cursoRepository.save(curso);
		
		return new CursoResponseDto(CursoSalvo);
	}
}
