package br.edu.utfpr.inteligenteacademy.model.dto.resource;

import br.edu.utfpr.inteligenteacademy.entity.LessonResource;

import java.time.Instant;

public record LessonResourceResponseDto(
    Long id,
    Long lessonId,
    String title,
    String content,
    Instant createdAt,
    Instant modifiedAt
) {
    public LessonResourceResponseDto(LessonResource lessonResource){
        this(
                lessonResource.getId(),
                lessonResource.getLesson().getId(),
                lessonResource.getTitle(),
                lessonResource.getContent(),
                lessonResource.getCreatedAt(),
                lessonResource.getModifiedAt()
        );
    }
}