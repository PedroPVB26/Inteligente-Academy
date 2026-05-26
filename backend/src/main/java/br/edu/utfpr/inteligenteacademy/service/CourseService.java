package br.edu.utfpr.inteligenteacademy.service;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.Course;
import br.edu.utfpr.inteligenteacademy.entity.Tag;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.CourseRepository;


@Service
public class CourseService {
	private final CourseRepository courseRepository;
	private final TagService tagService;
	
	public CourseService(CourseRepository courseRepository, TagService tagService) {
		this.courseRepository = courseRepository;
		this.tagService = tagService;
	}
	
	@Transactional(readOnly = true)
	public List<CourseResponseDto> findAll(){
		List<Course> courses = courseRepository.findAll();
		return courses.stream().map(x -> new CourseResponseDto(x)).toList();
	}
	
	
	@Transactional(readOnly = true)
	public CourseResponseDto findById(Long courseId) {
		Course course =
		        courseRepository.findById(courseId)
		        .orElseThrow(() ->
			        new ResourceNotFoundException(
			                "Course with id "
			                + courseId
			                + " not found"
			        )
		        );
		return new CourseResponseDto(course);
	}
	
	@Transactional
	public CourseResponseDto save(CourseCreationDto courseCreationDto) {

		if(courseRepository.existsByName(courseCreationDto.getName())) {
			throw new DatabaseException("Name already exists in the database");
		}
		
		Course course = new Course(courseCreationDto);

		if(courseCreationDto.getTagsIds() != null && !courseCreationDto.getTagsIds().isEmpty()) {
			for (Long tagId : courseCreationDto.getTagsIds()) {
				Tag tag = tagService.findEntityById(tagId);
				course.addTag(tag);
			}
		}
		
		
		Course savedCourse = courseRepository.save(course);
		
		return new CourseResponseDto(savedCourse);
	}
}
