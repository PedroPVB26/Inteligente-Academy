package br.edu.utfpr.inteligenteacademy.model.dto.enrollment;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotNull;

@Schema(
        name = "Enrollment Request Create",
        description = "Data required to request enrollment in a course"
)
public record EnrollmentRequestCreationDto(

        @NotNull(message = "Course id is required")
        @Schema(
                description = "Identifier of the course the user wants to enroll in",
                example = "1",
                requiredMode = Schema.RequiredMode.REQUIRED
        )
        Long courseId

) {
}