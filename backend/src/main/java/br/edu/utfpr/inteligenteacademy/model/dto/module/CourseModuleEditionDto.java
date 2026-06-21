package br.edu.utfpr.inteligenteacademy.model.dto.module;

import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO used to edit a module inside a course")
public record CourseModuleEditionDto(

        @Schema(
                description = "Title of the module",
                example = "Introduction to Spring Boot"
        )
        @Size(
                min = 3,
                max = 120,
                message = "title must contain between 3 and 120 characters"
        )
        String title,

        @Schema(
                description = "Detailed description of the module content",
                example = "This module introduces the fundamentals of Spring Boot framework"
        )
        @Size(
                min = 3,
                max = 500,
                message = "description must contain between 3 and 500 characters"
        )
        String description,

        @Schema(
                description = "Position of the module inside the course (ordering). Must be unique",
                example = "1"
        )
        @Positive(message = "position must be a positive number")
        Integer position,

        @Schema(
                description = "Publication status of the module",
                example = "PUBLISHED"
        )
        PublicationStatus publicationStatus

) {
}