package br.edu.utfpr.inteligenteacademy.listener;

import br.edu.utfpr.inteligenteacademy.entity.CourseModule;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.event.CourseModuleModifiedEvent;
import br.edu.utfpr.inteligenteacademy.model.event.LessonModifiedEvent;
import br.edu.utfpr.inteligenteacademy.repository.CourseModuleRepository;
import br.edu.utfpr.inteligenteacademy.repository.LessonRepository;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CourseModuleEventListener {
    private final CourseModuleRepository courseModuleRepository;
    private final LessonRepository lessonRepository;
    private final ApplicationEventPublisher applicationEventPublisher;

    public CourseModuleEventListener(
            CourseModuleRepository courseModuleRepository,
            LessonRepository lessonRepository,
            ApplicationEventPublisher applicationEventPublisher
    ) {
        this.courseModuleRepository = courseModuleRepository;
        this.lessonRepository = lessonRepository;
        this.applicationEventPublisher = applicationEventPublisher;
    }

    @EventListener
    public void onLessonModified(LessonModifiedEvent event) {
        CourseModule courseModule = courseModuleRepository.findById(event.courseModuleId()).orElseThrow(
                () -> new ResourceNotFoundException("Course module with id " +event.courseModuleId() + " was not found")
        );

        Long duration = lessonRepository.sumDurationByModuleId(event.courseModuleId());
        courseModule.setDurationInMinutes(duration);
        courseModuleRepository.save(courseModule);

        applicationEventPublisher.publishEvent(new CourseModuleModifiedEvent(courseModule.getCourse().getId()));
    }
}
