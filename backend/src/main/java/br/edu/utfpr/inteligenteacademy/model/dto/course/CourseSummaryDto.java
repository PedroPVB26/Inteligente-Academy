package br.edu.utfpr.inteligenteacademy.model.dto.course;

import br.edu.utfpr.inteligenteacademy.entity.Course;
import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonPropertyOrder({
        "id",
        "name",
        "description",
        "duration",
        "publicationStatus"
})
@Getter
@Setter
@NoArgsConstructor
public class CourseSummaryDto {

    private Long id;
    private String name;
    private String description;
    private Long duration;
    private PublicationStatus publicationStatus;

    public CourseSummaryDto(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.description = course.getDescription();
        this.duration = course.getDurationInMinutes();
        this.publicationStatus = course.getPublicationStatus();
    }
}