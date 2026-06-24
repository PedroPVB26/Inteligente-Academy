package br.edu.utfpr.inteligenteacademy.service;

import java.util.List;

import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseEditionDto;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseSummaryDto;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.Course;
import br.edu.utfpr.inteligenteacademy.entity.Tag;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.CourseRepository;


@AllArgsConstructor
@Service
public class CourseService {
	private final CourseRepository courseRepository;
	private final TagService tagService;

	@Transactional(readOnly = true)
	public List<CourseSummaryDto> findAll(PublicationStatus publicationStatus, Long tagId) {
		List<Course> courses;

		if (tagId != null && publicationStatus != null) {
			courses = courseRepository.findDistinctByPublicationStatusAndCourseTags_Tag_Id(publicationStatus, tagId);
		} else if (tagId != null) {
			courses = courseRepository.findDistinctByCourseTags_Tag_Id(tagId);
		} else if (publicationStatus != null) {
			courses = courseRepository.findByPublicationStatus(publicationStatus);
		} else {
			courses = courseRepository.findAll();
		}

		return courses.stream()
				.map(CourseSummaryDto::new)
				.toList();
	}

	@Transactional(readOnly = true)
	Course findEntityById(Long courseId) {
		return courseRepository.findById(courseId)
				.orElseThrow(() ->
						new ResourceNotFoundException(
								"Course with id: "
										+ courseId
										+ " not found."
						)
				);
	}

	@Transactional(readOnly = true)
	public CourseResponseDto findById(Long courseId) {
		Course course = findEntityById(courseId);
		return new CourseResponseDto(course);
	}

    @Transactional
    public void delete(Long courseId) {
        Course course = findEntityById(courseId);
        courseRepository.delete(course);
    }

	@Transactional
	public CourseResponseDto save(CourseCreationDto courseCreationDto) {
		if (courseRepository.existsByName(courseCreationDto.getName())) {
			throw new DatabaseException("Name already exists in the database");
		}

		Course course = new Course(courseCreationDto);

		if (courseCreationDto.getTagsIds() != null && !courseCreationDto.getTagsIds().isEmpty()) {
			for (Long tagId : courseCreationDto.getTagsIds()) {
				Tag tag = tagService.findEntityById(tagId);
				course.addTag(tag);
			}
		}

		Course savedCourse = courseRepository.save(course);
		return new CourseResponseDto(savedCourse);
	}

	@Transactional
	public CourseSummaryDto update(Long courseId, CourseEditionDto courseEditionDto) {
		Course course = findEntityById(courseId);

		if (courseEditionDto.name() != null
				&& courseRepository.existsByNameAndIdNot(courseEditionDto.name(), courseId)) {
			throw new DatabaseException("Name already exists in the database");
		}

		if (courseEditionDto.name() != null) {
			course.setName(courseEditionDto.name());
		}

		if (courseEditionDto.description() != null) {
			course.setDescription(courseEditionDto.description());
		}

		if (courseEditionDto.publicationStatus() != null) {
			course.setPublicationStatus(courseEditionDto.publicationStatus());
		}

		if (courseEditionDto.tagsIds() != null) {
			course.getCourseTags().clear();

			for (Long tagId : courseEditionDto.tagsIds()) {
				Tag tag = tagService.findEntityById(tagId);
				course.addTag(tag);
			}
		}

		Course updatedCourse = courseRepository.save(course);
		return new CourseSummaryDto(updatedCourse);
	}
}
