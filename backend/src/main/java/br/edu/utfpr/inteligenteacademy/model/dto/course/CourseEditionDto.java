package br.edu.utfpr.inteligenteacademy.model.dto.course;

import java.util.List;

import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CourseEditionDto(
    @Size(
            min = 3,
            max = 120,
            message = "name must contain between 3 and 120 characters"
    )
    String name,

    @Size(
            min = 3,
            max = 255,
            message = "description must contain between 3 and 255 characters"
    )
    String description,

    PublicationStatus publicationStatus,

    List<@Positive Long> tagsIds

) {}