package br.edu.utfpr.inteligenteacademy.model.dto.course;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CourseCreationDto {

    @NotBlank(message = "name must not be blank")
    @Size(
        min = 3,
        max = 120,
        message = "name must contain between 3 and 120 characters"
    )
    private String name;

    @NotBlank(message = "description must not be blank")
    @Size(
        min = 3,
        max = 255,
        message = "description must contain between 3 and 255 characters"
    )
    private String description;

    private List<@Positive Long> tagsIds;

}