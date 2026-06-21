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
        "durationInSeconds",
        "publicationStatus",
        "modulesCount",
        "lessonsCount"
})
@Getter
@Setter
@NoArgsConstructor
public class CourseSummaryDto {

    private Long id;
    private String name;
    private String description;
    private Long durationInSeconds;
    private PublicationStatus publicationStatus;
    private Integer moduleCount;
    private Integer lessonsCount;

    public CourseSummaryDto(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.description = course.getDescription();
        this.durationInSeconds = course.getDurationInSeconds();
        this.publicationStatus = course.getPublicationStatus();
        this.moduleCount = course.getCourseModules().size();
        this.lessonsCount = course.getCourseModules().stream()
                .mapToInt(module -> module.getLessons().size())
                .sum();
    }
}