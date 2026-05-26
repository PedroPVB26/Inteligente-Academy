package br.edu.utfpr.inteligenteacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.Tag;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.dto.etiqueta.EtiquetaCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.etiqueta.EtiquetaResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.EtiquetaRepository;


@Service
public class EtiquetaService {
	private final EtiquetaRepository etiquetaRepository;
	
	public EtiquetaService(EtiquetaRepository etiquetaRepository) {
		this.etiquetaRepository = etiquetaRepository;
	}
	
	@Transactional(readOnly = true)
	public List<EtiquetaResponseDto> findAll(){
		List<Tag> tags = etiquetaRepository.findAll();
		return tags.stream().map(x -> new EtiquetaResponseDto(x)).toList();
	}
	
	
	@Transactional(readOnly = true)
	public EtiquetaResponseDto findById(Long etiquetaId) {
		Tag tag =
		        etiquetaRepository.findById(etiquetaId)
		        .orElseThrow(() ->
			        new ResourceNotFoundException(
			                "Etiqueta with id "
			                + etiquetaId
			                + " not found"
			        )
		        );
		return new EtiquetaResponseDto(tag);
	}
	
	@Transactional(readOnly = true)
	public Tag findEntityById(Long etiquetaId) {
		return etiquetaRepository.findById(etiquetaId)
		        .orElseThrow(() ->
			        new ResourceNotFoundException(
			                "Etiqueta with id "
			                + etiquetaId
			                + " not found"
			        )
		        );
	}
	
	@Transactional
	public EtiquetaResponseDto save(EtiquetaCreationDto EtiquetaCreationDto) {

		if(etiquetaRepository.existsByName(EtiquetaCreationDto.getNome())) {
			throw new DatabaseException("Nome already exists in the database");
		}
		
		Tag Tag = new Tag(EtiquetaCreationDto);
		
		Tag tagSalvo = etiquetaRepository.save(Tag);
		
		return new EtiquetaResponseDto(tagSalvo);
	}
}
