package br.edu.utfpr.inteligenteacademy.listener;

import br.edu.utfpr.inteligenteacademy.model.event.LessonProgressUpdatedEvent;
import br.edu.utfpr.inteligenteacademy.service.EnrollmentService;
import lombok.RequiredArgsConstructor;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CourseCompletionListener {
    private final EnrollmentService enrollmentService;

    @EventListener
    public void onLessonProgressUpdateEvent(LessonProgressUpdatedEvent event){
        enrollmentService.evaluateCourseCompletion(event.enrollmentId());
    }
}
