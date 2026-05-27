package br.edu.utfpr.inteligenteacademy.service;

import br.edu.utfpr.inteligenteacademy.entity.Course;
import br.edu.utfpr.inteligenteacademy.entity.CourseModule;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.dto.module.CourseModuleCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.module.CourseModuleResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.CourseModuleRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CourseModuleService {
    private CourseModuleRepository courseModuleRepository;
    private CourseService courseService;

    public CourseModuleService(CourseModuleRepository courseModuleRepository, CourseService courseService) {
        this.courseModuleRepository = courseModuleRepository;
        this.courseService = courseService;
    }

    @Transactional(readOnly = true)
    public List<CourseModuleResponseDto> findAll() {
        List<CourseModule> courseModules = courseModuleRepository.findAll();
        return courseModules.stream().map(CourseModuleResponseDto::new).toList();
    }

    @Transactional(readOnly = true)
    public CourseModuleResponseDto findById(Long moduleId, Long courseId) {
        CourseModule courseModule = courseModuleRepository.findByIdAndCourseId(moduleId, courseId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Module with id: " + moduleId + " not found."
                ));
        return new CourseModuleResponseDto(courseModule);
    }

    @Transactional(readOnly = true)
    public CourseModule findEntityById(Long moduleId) {
        return courseModuleRepository.findById(moduleId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Module with id: " + moduleId + " not found."
                ));
    }


    @Transactional(readOnly = true)
    public List<CourseModuleResponseDto> findAllByCourseId(Long courseId) {
        courseService.findEntityById(courseId);

        List<CourseModule> courseModules = courseModuleRepository.findByCourseId(courseId);

        return courseModules.stream()
                .map(CourseModuleResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public CourseModule findEntityByIdAndCourseId(
            Long moduleId,
            Long courseId
    ) {

        return courseModuleRepository
                .findByIdAndCourseId(moduleId, courseId)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Module with id "
                                        + moduleId
                                        + " not found for course "
                                        + courseId
                        )
                );
    }

    @Transactional
    public CourseModuleResponseDto save(CourseModuleCreationDto courseModuleCreationDto, Long courseId) {
        Course course = courseService.findEntityById(courseId);

        if(courseModuleRepository.existsByCourseIdAndPosition(courseId, courseModuleCreationDto.getPosition())) {
            throw new DatabaseException("Course already has a module at position: " + courseModuleCreationDto.getPosition());
        }

        CourseModule courseModule = new CourseModule(courseModuleCreationDto, course);

        CourseModule moduleSaved = courseModuleRepository.save(courseModule);
        return new  CourseModuleResponseDto(moduleSaved);
    }
}
