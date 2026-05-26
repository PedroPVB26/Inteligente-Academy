package br.edu.utfpr.inteligenteacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.Course;
import br.edu.utfpr.inteligenteacademy.entity.Tag;
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
		List<Course> courses = cursoRepository.findAll();
		return courses.stream().map(x -> new CursoResponseDto(x)).toList();
	}
	
	
	@Transactional(readOnly = true)
	public CursoResponseDto findById(Long cursoId) {
		Course Course =
		        cursoRepository.findById(cursoId)
		        .orElseThrow(() ->
			        new ResourceNotFoundException(
			                "Curso with id "
			                + cursoId
			                + " not found"
			        )
		        );
		return new CursoResponseDto(Course);
	}
	
	@Transactional
	public CursoResponseDto save(CursoCreationDto cursoCreationDto) {

		if(cursoRepository.existsByName(cursoCreationDto.getNome())) {
			throw new DatabaseException("Nome already exists in the database");
		}
		
		Course course = new Course(cursoCreationDto);

		if(cursoCreationDto.getEtiquetasIds() != null && !cursoCreationDto.getEtiquetasIds().isEmpty()) {
			for (Long etiquetaId : cursoCreationDto.getEtiquetasIds()) {
				Tag tag = etiquetaService.findEntityById(etiquetaId);
				course.adicionarEtiqueta(tag);
			}
		}
		
		
		Course courseSalvo = cursoRepository.save(course);
		
		return new CursoResponseDto(courseSalvo);
	}
}
