package br.edu.utfpr.inteligenteacademy.repository;

import br.edu.utfpr.inteligenteacademy.entity.CourseModule;
import br.edu.utfpr.inteligenteacademy.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourseModuleId(Long courseModuleId);
    boolean existsByCourseModuleIdAndPosition(
            Long courseModuleId,
            Integer position
    );
}
