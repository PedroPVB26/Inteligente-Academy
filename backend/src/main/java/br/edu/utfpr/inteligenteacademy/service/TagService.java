package br.edu.utfpr.inteligenteacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.Tag;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.dto.tag.TagCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.tag.TagResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.TagRepository;


@Service
public class TagService {
	private final TagRepository tagRepository;
	
	public TagService(TagRepository tagRepository) {
		this.tagRepository = tagRepository;
	}
	
	@Transactional(readOnly = true)
	public List<TagResponseDto> findAll(){
		List<Tag> tags = tagRepository.findAll();
		return tags.stream().map(TagResponseDto::new).toList();
	}
	
	
	@Transactional(readOnly = true)
	public TagResponseDto findById(Long tagId) {
		Tag tag =
		        tagRepository.findById(tagId)
		        .orElseThrow(() ->
			        new ResourceNotFoundException(
			                "tag with id "
			                + tagId
			                + " not found"
			        )
		        );
		return new TagResponseDto(tag);
	}
	
	@Transactional(readOnly = true)
	public Tag findEntityById(Long tagId) {
		return tagRepository.findById(tagId)
		        .orElseThrow(() ->
			        new ResourceNotFoundException(
			                "tag with id "
			                + tagId
			                + " not found"
			        )
		        );
	}
	
	@Transactional
	public TagResponseDto save(TagCreationDto tagCreationDto) {

		if(tagRepository.existsByName(tagCreationDto.getName())) {
			throw new DatabaseException("Name already exists in the database");
		}
		
		Tag Tag = new Tag(tagCreationDto);
		
		Tag savedTag = tagRepository.save(Tag);
		
		return new TagResponseDto(savedTag);
	}
}
