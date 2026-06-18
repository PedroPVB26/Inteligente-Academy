package br.edu.utfpr.inteligenteacademy.model.dto.resource;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record LessonResourceCreationDto(

    @NotNull(message = "Lesson ID is required")
    Long lessonId,

    @NotBlank(message = "Title is required")
    @Size(max = 120, message = "Title must be at most 120 characters")
    String title,

    @NotBlank(message = "Content is required")
    String content
) {}