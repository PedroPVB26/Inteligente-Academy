package br.edu.utfpr.inteligenteacademy.entity;

import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.lesson.LessonCreationDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="module_id", nullable=false)
    private CourseModule courseModule;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false)
    private Integer position; // primeira, segunda aula do modulo?

    @Column(nullable = false)
    private Long durationInSeconds;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PublicationStatus publicationStatus = PublicationStatus.DRAFT;

    @Column(nullable = false)
    private String videoUrl;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant modifiedAt;

    public Lesson(LessonCreationDto  lessonCreationDto, CourseModule courseModule) {
        this.title = lessonCreationDto.getTitle();
        this.position = lessonCreationDto.getPosition();
        this.courseModule = courseModule;
        this.durationInSeconds = lessonCreationDto.getDurationInSeconds();
        this.videoUrl = lessonCreationDto.getVideoUrl();
    }


}
