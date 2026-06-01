package br.edu.utfpr.inteligenteacademy.repository;

import br.edu.utfpr.inteligenteacademy.entity.Lesson;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LessonRepository extends JpaRepository<Lesson, Long> {
    List<Lesson> findByCourseModuleId(Long courseModuleId);

    boolean existsByCourseModuleIdAndPosition(
            Long courseModuleId,
            Integer position
    );

    Optional<Lesson> findByIdAndCourseModuleIdAndCourseModuleCourseId(
            Long lessonId,
            Long moduleId,
            Long courseId
    );

    @Query("""
        SELECT COALESCE(SUM(l.durationInMinutes), 0)
        FROM Lesson l
        WHERE l.courseModule.id = :moduleId
    """)
    Long sumDurationByModuleId(Long moduleId);
}
