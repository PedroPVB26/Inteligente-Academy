package br.edu.utfpr.inteligenteacademy.listener;

import br.edu.utfpr.inteligenteacademy.entity.Course;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.event.CourseModuleModifiedEvent;
import br.edu.utfpr.inteligenteacademy.repository.CourseModuleRepository;
import br.edu.utfpr.inteligenteacademy.repository.CourseRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class CourseEventListener {
    private final CourseRepository courseRepository;
    private final CourseModuleRepository courseModuleRepository;

    public CourseEventListener(
            CourseRepository courseRepository,
            CourseModuleRepository courseModuleRepository
    ) {
        this.courseRepository = courseRepository;
        this.courseModuleRepository = courseModuleRepository;
    }

    @EventListener
    public void onCourseModuleModified(CourseModuleModifiedEvent event) {
        Course course = courseRepository.findById(event.courseId()).orElseThrow(
                () -> new ResourceNotFoundException("Course with id " + event.courseId() + " was not found")
        );

        Long duration = courseModuleRepository.sumDurationByCourseId(course.getId());
        course.setDurationInMinutes(duration);
        courseRepository.save(course);
    }
}
