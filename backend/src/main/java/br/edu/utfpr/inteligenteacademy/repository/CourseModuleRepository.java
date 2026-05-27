package br.edu.utfpr.inteligenteacademy.repository;

import br.edu.utfpr.inteligenteacademy.entity.CourseModule;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CourseModuleRepository extends JpaRepository<CourseModule, Long> {
    List<CourseModule> findByCourseId(Long courseId);
    boolean existsByCourseIdAndPosition(Long courseId, Integer position);
    Optional<CourseModule> findByIdAndCourseId(Long moduleId, Long courseId);
}
