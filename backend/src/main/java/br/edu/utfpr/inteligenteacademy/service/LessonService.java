package br.edu.utfpr.inteligenteacademy.service;


import br.edu.utfpr.inteligenteacademy.entity.CourseModule;
import br.edu.utfpr.inteligenteacademy.entity.Lesson;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.model.dto.lesson.LessonCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.lesson.LessonResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.CourseModuleRepository;
import br.edu.utfpr.inteligenteacademy.repository.LessonRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LessonService {
    private LessonRepository lessonRepository;
    private CourseModuleService courseModuleService;

    public LessonService(LessonRepository lessonRepository, CourseModuleService courseModuleService) {
        this.lessonRepository = lessonRepository;
        this.courseModuleService = courseModuleService;
    }

    @Transactional
    public LessonResponseDto save(LessonCreationDto lessonCreationDto, Long moduleId) {
        CourseModule courseModule = courseModuleService.findEntityById(moduleId);

        if(lessonRepository.existsByCourseModuleIdAndPosition(moduleId, lessonCreationDto.getPosition())) {
            throw new DatabaseException("Module already has a module at position: " + lessonCreationDto.getPosition());
        }

        Lesson lesson = new Lesson(lessonCreationDto, courseModule);
        Lesson saved = lessonRepository.save(lesson);
        return new LessonResponseDto(saved);
    }
}
