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
        SELECT COALESCE(SUM(l.durationInSeconds), 0)
        FROM Lesson l
        WHERE l.courseModule.id = :moduleId
    """)
    Long sumDurationByModuleId(Long moduleId);

    // Conta quantas aulas existem em todo o curso (através do relacionamento CourseModule -> Course)
    long countByCourseModuleCourseId(Long courseId);

    @Query("""
    SELECT COUNT(l)
    FROM Lesson l
    WHERE l.courseModule.course.id = :courseId
    AND l.publicationStatus =
        br.edu.utfpr.inteligenteacademy.model.PublicationStatus.PUBLISHED
""")
    long countPublishedLessonsByCourse(
            Long courseId
    );


    boolean existsByCourseModuleIdAndPositionAndIdNot(
            Long courseModuleId,
            Integer position,
            Long lessonId
    );
}
