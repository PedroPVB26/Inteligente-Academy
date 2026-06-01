package br.edu.utfpr.inteligenteacademy.controller;

import br.edu.utfpr.inteligenteacademy.model.dto.resource.LessonResourceCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.resource.LessonResourceResponseDto;
import br.edu.utfpr.inteligenteacademy.service.LessonResourceService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/lesson-resource")
public class LessonResourceController {
    private final LessonResourceService lessonResourceService;

    public LessonResourceController(LessonResourceService lessonResourceService) {
        this.lessonResourceService = lessonResourceService;
    }

    // ----- GET -----
    @GetMapping("/{id}")
    public ResponseEntity<LessonResourceResponseDto> findById(
            @PathVariable Long id
    ) {
        LessonResourceResponseDto responseDto = lessonResourceService.findById(id);
        return ResponseEntity.ok(responseDto);
    }

    @GetMapping
    public ResponseEntity<List<LessonResourceResponseDto>> findAllByLessonId(
            @RequestParam Long lessonId
    ) {
        List<LessonResourceResponseDto> resources = lessonResourceService.findAllByLessonId(lessonId);
        return ResponseEntity.ok(resources);
    }

    // ----- POST -----
    @PostMapping
    public ResponseEntity<LessonResourceResponseDto> save(
            @RequestBody @Valid LessonResourceCreationDto lessonResourceCreationDto
    ) {
        LessonResourceResponseDto responseDto = lessonResourceService.save(lessonResourceCreationDto);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(responseDto);
    }
}

