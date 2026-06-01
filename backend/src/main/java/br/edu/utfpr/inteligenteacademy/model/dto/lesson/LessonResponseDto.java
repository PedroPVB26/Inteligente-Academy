package br.edu.utfpr.inteligenteacademy.model.dto.lesson;

import br.edu.utfpr.inteligenteacademy.entity.Lesson;
import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@JsonPropertyOrder({
        "id",
        "title",
        "position",
        "publicationStatus",
        "durationInMinutes",
        "videoUrl",
        "createdAt",
        "modifiedAt"
})
@Getter
@Setter
public class LessonResponseDto {

    private Long id;
    private String title;
    private Integer position;
    private PublicationStatus publicationStatus;
    private Long durationInMinutes;
    private String videoUrl;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public LessonResponseDto(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.position = lesson.getPosition();
        this.publicationStatus = lesson.getPublicationStatus();
        this.durationInMinutes = lesson.getDurationInMinutes();
        this.videoUrl = lesson.getVideoUrl();
        this.createdAt = lesson.getCreatedAt();
        this.modifiedAt = lesson.getModifiedAt();
    }
}