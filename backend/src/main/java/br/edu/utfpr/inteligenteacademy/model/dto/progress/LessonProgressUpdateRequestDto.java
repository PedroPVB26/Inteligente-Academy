package br.edu.utfpr.inteligenteacademy.model.dto.progress;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

@Schema(
        name = "LessonProgressUpdateRequest",
        description = "Request used to update a student's lesson progress."
)
public record LessonProgressUpdateRequestDto(

        @Schema(
                description = "Unique identifier of the lesson.",
                example = "35"
        )
        @NotNull(message = "lessonId must not be null")
        @Positive(message = "lessonId must be positive")
        Long lessonId,

        @Schema(
                description = "Highest position reached by the student in the lesson video, measured in seconds.",
                example = "542",
                minimum = "0"
        )
        @NotNull(message = "highestPositionReachedSeconds must not be null")
        @PositiveOrZero(
                message = "highestPositionReachedSeconds must be greater than or equal to zero"
        )
        Long highestPositionReachedSeconds

) {
}