package br.edu.utfpr.inteligenteacademy.repository;

import br.edu.utfpr.inteligenteacademy.entity.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {
    List<CourseModule> findByCourseId(Long courseId);
    boolean existsByCourseIdAndPosition(Long courseId, Integer position);
    Optional<CourseModule> findByIdAndCourseId(Long moduleId, Long courseId);
    @Query("""
        SELECT COALESCE(SUM(m.durationInMinutes), 0)
        FROM CourseModule m
        WHERE m.course.id = :courseId
    """)
    Long sumDurationByCourseId(Long courseId);
}
