package br.edu.utfpr.inteligenteacademy.model.dto.module;

import br.edu.utfpr.inteligenteacademy.entity.CourseModule;
import br.edu.utfpr.inteligenteacademy.entity.Lesson;
import br.edu.utfpr.inteligenteacademy.model.dto.PublicationStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.lesson.LessonResponseDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

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
        "createdAt",
        "modifiedAt"
})
public class CourseModuleResponseDto {

    private Long id;
    private String title;
    private String description;
    private Integer position;
    private Long courseId;
    private PublicationStatus  publicationStatus;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    private List<LessonResponseDto> lessons;

    public CourseModuleResponseDto() {
    }

//    public ModuleResponseDto(
//            Long id,
//            String title,
//            String description,
//            Integer position,
//            Long courseId,
//            LocalDateTime createdAt,
//            LocalDateTime modifiedAt,
//            List<Lesson> lessons,
//            PublicationStatus  publicationStatus
//    ) {
//        this.id = id;
//        this.title = title;
//        this.description = description;
//        this.position = position;
//        this.courseId = courseId;
//        this.createdAt = createdAt;
//        this.modifiedAt = modifiedAt;
//        this.lessons = lessons;
//        this.publicationStatus = publicationStatus;
//    }

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
        this.courseId = courseModule.getCourse().getId();
        this.createdAt = courseModule.getCreatedAt();
        this.modifiedAt = courseModule.getModifiedAt();
    }


    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Integer getPosition() {
        return position;
    }

    public Long getCourseId() {
        return courseId;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public List<LessonResponseDto> getLessons() {
        return lessons;
    }

    public  PublicationStatus getPublicationStatus() {return publicationStatus;}


}