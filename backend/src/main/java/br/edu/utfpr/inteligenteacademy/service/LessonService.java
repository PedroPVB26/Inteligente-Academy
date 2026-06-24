package br.edu.utfpr.inteligenteacademy.service;


import br.edu.utfpr.inteligenteacademy.entity.CourseModule;
import br.edu.utfpr.inteligenteacademy.entity.Lesson;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.dto.lesson.LessonCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.lesson.LessonEditionDto;
import br.edu.utfpr.inteligenteacademy.model.dto.lesson.LessonResponseDto;
import br.edu.utfpr.inteligenteacademy.model.event.LessonModifiedEvent;
import br.edu.utfpr.inteligenteacademy.repository.LessonRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class LessonService {
    private final LessonRepository lessonRepository;
    private final CourseModuleService courseModuleService;
    private final ApplicationEventPublisher applicationEventPublisher;

    public LessonService(
            LessonRepository lessonRepository,
            CourseModuleService courseModuleService,
            ApplicationEventPublisher applicationEventPublisher) {
        this.lessonRepository = lessonRepository;
        this.courseModuleService = courseModuleService;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @Transactional(readOnly = true)
    public LessonResponseDto findById(Long lessonId) {
        Lesson lesson = findEntityById(lessonId);
        return new LessonResponseDto(lesson);
    }

    @Transactional(readOnly = true)
    Lesson findEntityById(Long lessonId) {
        return lessonRepository.findById(lessonId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Lesson with id: " + lessonId + " not found."
                ));
    }

    @Transactional(readOnly = true)
    public List<LessonResponseDto> findAllByModuleIdAndCourseId(Long moduleId, Long courseId){
        courseModuleService.findById(moduleId, courseId);

        List<Lesson> lessons = lessonRepository.findByCourseModuleId(moduleId);

        return lessons.stream()
                .map(LessonResponseDto::new)
                .toList();
    }

    @Transactional(readOnly = true)
    public LessonResponseDto findByHierarchy(
            Long courseId,
            Long moduleId,
            Long lessonId){
        Lesson lesson = lessonRepository.findByIdAndCourseModuleIdAndCourseModuleCourseId(
                lessonId, moduleId, courseId
        ).orElseThrow(()-> new ResourceNotFoundException("Lesson with id: " + lessonId + " not found."));
        return new LessonResponseDto(lesson);
    }

    @Transactional
    public LessonResponseDto save(LessonCreationDto lessonCreationDto, Long moduleId, Long courseId) {
        CourseModule courseModule = courseModuleService.findEntityByIdAndCourseId(moduleId, courseId);

        if(lessonRepository.existsByCourseModuleIdAndPosition(moduleId, lessonCreationDto.getPosition())) {
            throw new DatabaseException("Module already has a lesson at position: " + lessonCreationDto.getPosition());
        }

        Lesson lesson = new Lesson(lessonCreationDto, courseModule);
        Lesson saved = lessonRepository.save(lesson);

        publishEvent(new LessonModifiedEvent(courseModule.getId()));
        return new LessonResponseDto(saved);
    }

    @Transactional
    public void delete(Long courseId, Long moduleId, Long lessonId) {
        Lesson lesson = lessonRepository.findByIdAndCourseModuleIdAndCourseModuleCourseId(
                lessonId, moduleId, courseId
        ).orElseThrow(() -> new ResourceNotFoundException(
                "Lesson with id: " + lessonId + " not found."
        ));
        lessonRepository.delete(lesson);
        publishEvent(new LessonModifiedEvent(moduleId));
    }

    private void publishEvent(LessonModifiedEvent lessonModifiedEvent) {
        applicationEventPublisher.publishEvent(lessonModifiedEvent);
    }

    @Transactional
    public LessonResponseDto update(
            Long courseId,
            Long moduleId,
            Long lessonId,
            LessonEditionDto lessonEditionDto
    ) {
        Lesson lesson = lessonRepository.findByIdAndCourseModuleIdAndCourseModuleCourseId(
                lessonId, moduleId, courseId
        ).orElseThrow(() -> new ResourceNotFoundException(
                "Lesson with id: " + lessonId + " not found."
        ));

        if (lessonEditionDto.position() != null
                && lessonRepository.existsByCourseModuleIdAndPositionAndIdNot(
                moduleId,
                lessonEditionDto.position(),
                lessonId
        )) {
            throw new DatabaseException(
                    "Module already has a lesson at position: " + lessonEditionDto.position()
            );
        }

        if (lessonEditionDto.title() != null) {
            lesson.setTitle(lessonEditionDto.title());
        }

        if (lessonEditionDto.position() != null) {
            lesson.setPosition(lessonEditionDto.position());
        }

        if (lessonEditionDto.durationInSeconds() != null) {
            lesson.setDurationInSeconds(lessonEditionDto.durationInSeconds());
        }

        if (lessonEditionDto.videoUrl() != null) {
            lesson.setVideoUrl(lessonEditionDto.videoUrl());
        }

        if (lessonEditionDto.publicationStatus() != null) {
            lesson.setPublicationStatus(lessonEditionDto.publicationStatus());
        }

        Lesson updated = lessonRepository.save(lesson);

        publishEvent(new LessonModifiedEvent(moduleId));
        return new LessonResponseDto(updated);
    }

}
