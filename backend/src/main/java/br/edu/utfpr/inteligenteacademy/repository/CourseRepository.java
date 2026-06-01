package br.edu.utfpr.inteligenteacademy.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.Course;

public interface CourseRepository extends JpaRepository<Course, Long>{
	boolean existsByName(String name);
}
