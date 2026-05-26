package br.edu.utfpr.inteligenteacademy.model.dto.lesson;

import br.edu.utfpr.inteligenteacademy.entity.Lesson;
import br.edu.utfpr.inteligenteacademy.model.dto.PublicationStatus;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import java.time.LocalDateTime;

@JsonPropertyOrder({
        "id",
        "title",
        "position",
        "publicationStatus",
        "createdAt",
        "modifiedAt"
})
public class LessonResponseDto {

    private Long id;
    private String title;
    private Integer position;
    private PublicationStatus publicationStatus;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public LessonResponseDto() {
    }

    public LessonResponseDto(Long id, String title, Integer position,
                             PublicationStatus publicationStatus,
                             LocalDateTime createdAt,
                             LocalDateTime modifiedAt) {
        this.id = id;
        this.title = title;
        this.position = position;
        this.publicationStatus = publicationStatus;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public LessonResponseDto(Lesson lesson) {
        this.id = lesson.getId();
        this.title = lesson.getTitle();
        this.position = lesson.getPosition();
        this.publicationStatus = lesson.getPublicationStatus();
        this.createdAt = lesson.getCreatedAt();
        this.modifiedAt = lesson.getModifiedAt();
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public Integer getPosition() {
        return position;
    }

    public PublicationStatus getPublicationStatus() {
        return publicationStatus;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public void setPublicationStatus(PublicationStatus publicationStatus) {
        this.publicationStatus = publicationStatus;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }
}