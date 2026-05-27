package br.edu.utfpr.inteligenteacademy.model.dto.lesson;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

@Schema(description = "DTO used to create a lesson inside a module")
public class LessonCreationDto {

    @Schema(
            description = "Title of the lesson",
            example = "Introduction to REST APIs"
    )
    @NotBlank(message = "title must not be blank")
    @Size(min = 3, max = 120, message = "title must contain between 3 and 120 characters")
    private String title;

    @Schema(
            description = "Position of the lesson inside the module (ordering). Must be unique",
            example = "1"
    )
    @NotNull(message = "position must not be null")
    @Positive(message = "position must be a positive number")
    private Integer position;

    public LessonCreationDto() {
    }

    public LessonCreationDto(String title, Integer position) {
        this.title = title;
        this.position = position;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }
}