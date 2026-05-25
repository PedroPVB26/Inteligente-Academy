package br.edu.utfpr.inteligenteacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.Curso;
import br.edu.utfpr.inteligenteacademy.entity.Etiqueta;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.dto.curso.CursoCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.curso.CursoResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.CursoRepository;


@Service
public class CursoService {
	private final CursoRepository cursoRepository;
	private final EtiquetaService etiquetaService;
	
	public CursoService(CursoRepository cursoRepository, EtiquetaService etiquetaService) {
		this.cursoRepository = cursoRepository;
		this.etiquetaService = etiquetaService;
	}
	
	@Transactional(readOnly = true)
	public List<CursoResponseDto> findAll(){
		List<Curso> Cursos = cursoRepository.findAll();
		return Cursos.stream().map(x -> new CursoResponseDto(x)).toList();
	}
	
	
	@Transactional(readOnly = true)
	public CursoResponseDto findById(Long cursoId) {
		Curso Curso =
		        cursoRepository.findById(cursoId)
		        .orElseThrow(() ->
			        new ResourceNotFoundException(
			                "Curso with id "
			                + cursoId
			                + " not found"
			        )
		        );
		return new CursoResponseDto(Curso);
	}
	
	@Transactional
	public CursoResponseDto save(CursoCreationDto cursoCreationDto) {

		if(cursoRepository.existsByNome(cursoCreationDto.getNome())) {
			throw new DatabaseException("Nome already exists in the database");
		}
		
		Curso curso = new Curso(cursoCreationDto);

		if(cursoCreationDto.getEtiquetasIds() != null && !cursoCreationDto.getEtiquetasIds().isEmpty()) {
			for (Long etiquetaId : cursoCreationDto.getEtiquetasIds()) {
				Etiqueta etiqueta = etiquetaService.findEntityById(etiquetaId);
				curso.adicionarEtiqueta(etiqueta);
			}
		}
		
		
		Curso CursoSalvo = cursoRepository.save(curso);
		
		return new CursoResponseDto(CursoSalvo);
	}
}
