package br.edu.utfpr.inteligenteacademy.repository;

import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import br.edu.utfpr.inteligenteacademy.entity.Course;

import java.util.List;

public interface CourseRepository extends JpaRepository<Course, Long>{
	boolean existsByName(String name);
	boolean existsByNameAndIdNot(String name, Long id);
	List<Course> findByPublicationStatus(PublicationStatus publicationStatus);
}
