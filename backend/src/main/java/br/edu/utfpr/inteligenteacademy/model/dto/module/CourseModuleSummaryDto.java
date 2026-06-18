package br.edu.utfpr.inteligenteacademy.model.dto.module;

import br.edu.utfpr.inteligenteacademy.entity.CourseModule;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonPropertyOrder({
        "id",
        "title",
        "description",
        "position",
        "duration"
})
@Getter
@Setter
@NoArgsConstructor
public class CourseModuleSummaryDto {

    private Long id;
    private String title;
    private String description;
    private Integer position;
    private Long duration;

    public CourseModuleSummaryDto(CourseModule courseModule) {
        this.id = courseModule.getId();
        this.title = courseModule.getTitle();
        this.description = courseModule.getDescription();
        this.position = courseModule.getPosition();
        this.duration = courseModule.getDurationInMinutes();
    }
}