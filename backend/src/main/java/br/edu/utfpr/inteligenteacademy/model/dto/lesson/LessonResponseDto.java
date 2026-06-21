package br.edu.utfpr.inteligenteacademy.model.dto.lesson;

import br.edu.utfpr.inteligenteacademy.entity.Lesson;
import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import lombok.Getter;
import lombok.Setter;

import java.time.Instant;

@JsonPropertyOrder({
        "id",
        "title",
        "position",
        "publicationStatus",
        "durationInSeconds",
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
    private Long durationInSeconds;
    private String videoUrl;
    private Instant createdAt;
    private Instant modifiedAt;

    public LessonResponseDto(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.position = lesson.getPosition();
        this.publicationStatus = lesson.getPublicationStatus();
        this.durationInSeconds = lesson.getDurationInSeconds();
        this.videoUrl = lesson.getVideoUrl();
        this.createdAt = lesson.getCreatedAt();
        this.modifiedAt = lesson.getModifiedAt();
    }
}