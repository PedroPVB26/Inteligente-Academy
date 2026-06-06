package br.edu.utfpr.inteligenteacademy.model.dto.module;

import br.edu.utfpr.inteligenteacademy.entity.CourseModule;
import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.lesson.LessonResponseDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@JsonPropertyOrder({
        "id",
        "title",
        "description",
        "position",
        "courseId",
        "lessons",
        "publicationStatus",
        "duration",
        "createdAt",
        "modifiedAt"
})
@Getter
@Setter
public class CourseModuleResponseDto {

    private Long id;
    private String title;
    private String description;
    private Integer position;
    private Long courseId;
    private PublicationStatus  publicationStatus;
    private Long duration;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private List<LessonResponseDto> lessons;


    public CourseModuleResponseDto(CourseModule courseModule) {
        this.id = courseModule.getId();
        this.title = courseModule.getTitle();
        this.description = courseModule.getDescription();
        this.position = courseModule.getPosition();
        this.lessons = courseModule.getLessons()
                .stream()
                .map(LessonResponseDto::new)
                .toList();
        this.publicationStatus = courseModule.getPublicationStatus();
        this.duration = courseModule.getDurationInMinutes();
        this.courseId = courseModule.getCourse().getId();
        this.createdAt = courseModule.getCreatedAt();
        this.modifiedAt = courseModule.getModifiedAt();
    }
}