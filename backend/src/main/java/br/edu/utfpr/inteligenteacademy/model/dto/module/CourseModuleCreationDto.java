package br.edu.utfpr.inteligenteacademy.model.dto.module;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO used to create a module inside a course")
public class CourseModuleCreationDto {

    @Schema(
            description = "Title of the module",
            example = "Introduction to Spring Boot"
    )
    @NotBlank(message = "title must not be blank")
    @Size(min = 3, max = 120, message = "title must contain between 3 and 120 characters")
    private String title;

    @Schema(
            description = "Detailed description of the module content",
            example = "This module introduces the fundamentals of Spring Boot framework"
    )

    @NotBlank(message = "description must not be blank")
    @Size(min = 3, max = 500, message = "description must contain between 3 and 500 characters")
    private String description;

    @Schema(
            description = "Position of the module inside the course (ordering). Must be unique",
            example = "1"
    )
    @NotNull(message = "position must not be null")
    @Positive(message = "position must be a positive number")
    private Integer position;

    public CourseModuleCreationDto() {
    }

    public CourseModuleCreationDto(String title, String description, Integer position) {
        this.title = title;
        this.description = description;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}