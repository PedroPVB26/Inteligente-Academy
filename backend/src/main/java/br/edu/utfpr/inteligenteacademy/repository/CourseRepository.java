package br.edu.utfpr.inteligenteacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
    boolean existsById(Integer id);
	boolean existsByName(String name);
    boolean existsByDescription(String description);
	boolean existsByDuration(Integer duration);
}
