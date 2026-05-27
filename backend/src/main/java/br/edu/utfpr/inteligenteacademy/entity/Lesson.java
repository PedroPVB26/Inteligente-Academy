package br.edu.utfpr.inteligenteacademy.entity;

import br.edu.utfpr.inteligenteacademy.model.dto.PublicationStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.lesson.LessonCreationDto;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="module_id", nullable=false)
    private CourseModule courseModule;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, unique = true)
    private Integer position; // primeira, segunda aula do modulo?

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PublicationStatus publicationStatus = PublicationStatus.DRAFT;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    public Lesson() {
    }

    public Lesson(LessonCreationDto  lessonCreationDto, CourseModule courseModule) {
        this.title = lessonCreationDto.getTitle();
        this.position = lessonCreationDto.getPosition();
        this.courseModule = courseModule;
    }

    public CourseModule getModule() {
        return courseModule;
    }

    public void setModule(CourseModule courseModule) {
        this.courseModule = courseModule;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
    }

    public PublicationStatus getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(PublicationStatus publicationStatus) {
        this.publicationStatus = publicationStatus;
    }
}
