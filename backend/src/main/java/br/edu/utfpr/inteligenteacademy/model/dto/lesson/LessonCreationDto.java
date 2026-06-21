package br.edu.utfpr.inteligenteacademy.model.dto.lesson;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.URL;

@Schema(description = "DTO used to create a lesson inside a module")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
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

    @Schema(
            description = "Lesson durationInSeconds in seconds",
            example = "1200"
    )
    @NotNull(message = "durationInSeconds must not be null")
    @Positive(message = "durationInSeconds must be a positive number")
    private Long durationInSeconds;

    @Schema(
            description = "Lesson video URL",
            example = "https://www.youtube.com/watch?v=dQw4w9WgXcQ"
    )
    @NotBlank(message = "videoUrl must not be blank")
    @Size(
            min = 10,
            max = 2048,
            message = "videoUrl must contain between 10 and 2048 characters"
    )
    @URL(message = "videoUrl must be a valid URL")
    private String videoUrl;
}