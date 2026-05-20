package br.edu.utfpr.inteligenteacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.Etiqueta;
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
		List<Etiqueta> etiquetas = etiquetaRepository.findAll();
		return etiquetas.stream().map(x -> new EtiquetaResponseDto(x)).toList();
	}
	
	
	@Transactional(readOnly = true)
	public EtiquetaResponseDto findById(Integer etiquetaId) {
		Etiqueta etiqueta =
		        etiquetaRepository.findById(etiquetaId)
		        .orElseThrow(() ->
			        new ResourceNotFoundException(
			                "Etiqueta with id "
			                + etiquetaId
			                + " not found"
			        )
		        );
		return new EtiquetaResponseDto(etiqueta);
	}
	
	@Transactional
	public EtiquetaResponseDto save(EtiquetaCreationDto EtiquetaCreationDto) {

		if(etiquetaRepository.existsByNome(EtiquetaCreationDto.getNome())) {
			throw new DatabaseException("Nome already exists in the database");
		}
		
		Etiqueta Etiqueta = new Etiqueta(EtiquetaCreationDto);
		
		Etiqueta EtiquetaSalvo = etiquetaRepository.save(Etiqueta);
		
		return new EtiquetaResponseDto(EtiquetaSalvo);
	}
}
