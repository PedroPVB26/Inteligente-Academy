package br.edu.utfpr.inteligenteacademy.controller;

import br.edu.utfpr.inteligenteacademy.model.dto.lesson.LessonCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.lesson.LessonResponseDto;
import br.edu.utfpr.inteligenteacademy.service.LessonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("courses/{courseId}/modules/{moduleId}/lessons")
public class LessonController {
    private LessonService lessonService;

    public LessonController(LessonService lessonService) {
        this.lessonService = lessonService;
    }

    // ----- GET -----
    @GetMapping("/{lessonId}")
    public ResponseEntity<LessonResponseDto> findById(
            @PathVariable("moduleId") Long moduleId,
            @PathVariable("courseId") Long courseId,
            @PathVariable("lessonId") Long lessonId
    ){
        // tenho que verificar se o usuário que fez essa requisição está inscrito no curso para poder acessar a aula
        LessonResponseDto lessonResponse = lessonService
                .findByHierarchy(courseId, moduleId, lessonId);
        return ResponseEntity.ok(lessonResponse);
    }

    @GetMapping
    public ResponseEntity<List<LessonResponseDto>> findAllByModuleIdAndCourse(
            @PathVariable("moduleId") Long moduleId,
            @PathVariable("courseId") Long courseId
    ) {
        return ResponseEntity.ok(lessonService.findAllByModuleIdAndCourseId(moduleId, courseId));
    }

    // ----- POST -----
    @PostMapping
    public ResponseEntity<LessonResponseDto> save(
            @RequestBody @Valid LessonCreationDto lessonCreationDto,
            @PathVariable Long courseId,
            @PathVariable Long moduleId
    ) {

        LessonResponseDto lessonResponse =
                lessonService.save(
                        lessonCreationDto,
                        moduleId,
                        courseId
                );

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(lessonResponse);
    }
}
