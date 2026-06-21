package br.edu.utfpr.inteligenteacademy.controller;

import java.util.List;

import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseEditionDto;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseSummaryDto;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseResponseDto;
import br.edu.utfpr.inteligenteacademy.service.CourseService;


@RestController
@RequestMapping("/courses")
@AllArgsConstructor
public class CourseController {
    private final CourseService courseService;

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
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }
}