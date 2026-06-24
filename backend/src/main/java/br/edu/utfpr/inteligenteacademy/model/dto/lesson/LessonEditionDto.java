package br.edu.utfpr.inteligenteacademy.model.dto.lesson;

import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO used to partially edit a lesson")
public record LessonEditionDto(

        @Schema(
                description = "Lesson title",
                example = "Introduction to JPA"
        )
        @Size(
                min = 3,
                max = 120,
                message = "title must contain between 3 and 120 characters"
        )
        String title,

        @Schema(
                description = "Position of the lesson inside the module",
                example = "1"
        )
        @Positive(message = "position must be a positive number")
        Integer position,

        @Schema(
                description = "Duration in seconds",
                example = "600"
        )
        @Positive(message = "durationInSeconds must be a positive number")
        Long durationInSeconds,

        @Schema(
                description = "Video URL of the lesson",
                example = "https://www.youtube.com/watch?v=example"
        )
        @Size(
                min = 3,
                max = 255,
                message = "videoUrl must contain between 3 and 255 characters"
        )
        String videoUrl,

        @Schema(
                description = "Publication status of the lesson",
                example = "PUBLISHED"
        )
        PublicationStatus publicationStatus
) {}