package br.edu.utfpr.inteligenteacademy.controller;

import java.util.List;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseResponseDto;
import br.edu.utfpr.inteligenteacademy.service.CourseService;


@RestController
@RequestMapping("/courses")
public class CourseController {
    private CourseService courseService;

    public CourseController(CourseService courseService) {
        this.courseService = courseService;
    }

    // ----- GET -----
    @GetMapping
    public ResponseEntity<List<CourseResponseDto>> findAll() {
        return ResponseEntity.status(HttpStatus.OK).body(courseService.findAll());
    }

    @GetMapping("/{courseId}")
    public ResponseEntity<CourseResponseDto> findById(@PathVariable Long courseId) {
        CourseResponseDto courseResponseDto = courseService.findById(courseId);
        return ResponseEntity.status(HttpStatus.OK).body(courseResponseDto);
    }

    // ----- POST -----
    @PostMapping
    public ResponseEntity<CourseResponseDto> save(@RequestBody @Valid CourseCreationDto courseCreationDto) {
        CourseResponseDto CursoSalvo = courseService.save(courseCreationDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(CursoSalvo);
    }

    // ----- PUT -----


    // ----- DELETE -----

}