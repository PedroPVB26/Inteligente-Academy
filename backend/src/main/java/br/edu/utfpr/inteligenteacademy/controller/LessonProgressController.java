package br.edu.utfpr.inteligenteacademy.controller;

import br.edu.utfpr.inteligenteacademy.entity.User;
import br.edu.utfpr.inteligenteacademy.model.dto.progress.LessonProgressResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.progress.LessonProgressUpdateRequestDto;
import br.edu.utfpr.inteligenteacademy.service.LessonProgressService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import br.edu.utfpr.inteligenteacademy.model.dto.progress.CourseProgressResponseDto;

@RestController
@RequestMapping("/progress")
@RequiredArgsConstructor
public class LessonProgressController {
    private final LessonProgressService lessonProgressService;

    // Atualiza o progresso de uma aula para o usuário autenticado
    @PatchMapping
    public ResponseEntity<LessonProgressResponseDto> updateProgress(
            @RequestBody @Valid LessonProgressUpdateRequestDto dto,
            @AuthenticationPrincipal User user
    ) {
        LessonProgressResponseDto response = lessonProgressService.updateProgress(dto, user);
        return ResponseEntity.status(HttpStatus.OK).body(response);
    }

    @GetMapping("/course/{courseId}")
    public ResponseEntity<CourseProgressResponseDto> getCourseProgress(
            @PathVariable Long courseId,
            @AuthenticationPrincipal User user
    ) {
        CourseProgressResponseDto response = lessonProgressService.getCourseProgress(user, courseId);
        return ResponseEntity.ok(response);
    }

}
