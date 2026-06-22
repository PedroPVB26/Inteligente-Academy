package br.edu.utfpr.inteligenteacademy.controller;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseSummaryDto;
import br.edu.utfpr.inteligenteacademy.service.CourseService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping("/courses")
@AllArgsConstructor
@CrossOrigin(origins = "*")
public class CourseController {
    private final CourseService courseService;
    private static final Logger log = LoggerFactory.getLogger(CourseController.class);

    // ----- GET -----
    @GetMapping
    public ResponseEntity<List<CourseSummaryDto>> findAll(
            @RequestParam(required = false) PublicationStatus publicationStatus
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll(publicationStatus));
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDto> findById(@PathVariable Long courseId) {
        CourseResponseDto courseResponseDto = courseService.findById(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(courseResponseDto);
    }

    // ----- POST -----
    @PostMapping
    public ResponseEntity<CourseResponseDto> save(@RequestBody @Valid CourseCreationDto courseCreationDto) {
        CourseResponseDto saved = courseService.save(courseCreationDto);
        if (saved != null) {
            log.debug("Course created with id={}", saved.getId());
        } else {
            log.warn("Course save returned null for request: {}", courseCreationDto);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    // ----- DELETE -----
    @DeleteMapping("/{courseId}")
    public ResponseEntity<Void> delete(@PathVariable Long courseId) {
        courseService.delete(courseId);
        return ResponseEntity.noContent().build();
    }
}