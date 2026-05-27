package br.edu.utfpr.inteligenteacademy.service;

import br.edu.utfpr.inteligenteacademy.entity.Course;
import br.edu.utfpr.inteligenteacademy.entity.CourseModule;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.dto.module.CourseModuleCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.module.CourseModuleResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.CourseRepository;
import br.edu.utfpr.inteligenteacademy.repository.CourseModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseModuleService {
    private CourseModuleRepository courseModuleRepository;
    private CourseRepository courseRepository;

    public CourseModuleService(CourseModuleRepository courseModuleRepository, CourseRepository courseRepository) {
        this.courseModuleRepository = courseModuleRepository;
        this.courseRepository = courseRepository;
    }

    @Transactional(readOnly = true)
    public List<CourseModuleResponseDto> findAll() {
        List<CourseModule> courseModules = courseModuleRepository.findAll();
        return courseModules.stream().map(CourseModuleResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public CourseModuleResponseDto findById(Long moduleId) {
        CourseModule  courseModule = courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course module with id: " + moduleId + " not found."
                ));
        return new  CourseModuleResponseDto(courseModule);
    }

    @Transactional(readOnly = true)
    public CourseModule findEntityById(Long moduleId) {
        return courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course module with id: " + moduleId + " not found."
                ));
    }

    @Transactional(readOnly = true)
    public List<CourseModuleResponseDto> findByCourseId(Long courseId) {
        courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Course with id " + courseId + " not found"
                ));

        List<CourseModule> courseModules = courseModuleRepository.findByCourseId(courseId);

        return courseModules.stream()
                .map(CourseModuleResponseDto::new)
                .toList();
    }

    @Transactional
    public CourseModuleResponseDto save(CourseModuleCreationDto courseModuleCreationDto, Long courseId) {
        Course course = courseRepository.findById(courseId)
                .orElseThrow(() -> new ResourceNotFoundException("Course with id: " + courseId + " not found."));

        if(courseModuleRepository.existsByCourseIdAndPosition(courseId, courseModuleCreationDto.getPosition())) {
            throw new DatabaseException("Course already has a module at position: " + courseModuleCreationDto.getPosition());
        }

        CourseModule courseModule = new CourseModule(courseModuleCreationDto, course);

        CourseModule moduleSaved = courseModuleRepository.save(courseModule);
        return new  CourseModuleResponseDto(moduleSaved);
    }
}
