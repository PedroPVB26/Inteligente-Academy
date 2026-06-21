package br.edu.utfpr.inteligenteacademy.model.dto.progress;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(
    name = "LessonProgressResponse",
    description = "Represents a student's progress in a lesson."
)
public record LessonProgressResponseDto(

    @Schema(
        description = "Unique identifier of the enrollment.",
        example = "12"
    )
    Long enrollmentId,

    @Schema(
        description = "Unique identifier of the lesson.",
        example = "35"
    )
    Long lessonId,

    @Schema(
        description = "Highest position reached by the student in the lesson video, measured in seconds.",
        example = "542"
    )
    Long highestPositionReachedSeconds,

    @Schema(
        description = "Indicates whether the lesson has been completed.",
        example = "false"
    )
    Boolean completed,

    @Schema(
        description = "Lesson completion percentage.",
        example = "43.5"
    )
    Double completionPercentage

) {
}