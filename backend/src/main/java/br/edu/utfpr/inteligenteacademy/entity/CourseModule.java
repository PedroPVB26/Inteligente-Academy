package br.edu.utfpr.inteligenteacademy.entity;

import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.module.CourseModuleCreationDto;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class CourseModule {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="course_id", nullable=false)
    private Course course;

    @Column(nullable = false)
    private String title;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long durationInSeconds = 0L; // in seconds. É calculado automaticamente a partir das suas aulas

    @Column(nullable = false)
    private Integer position; // primeiro modulo do curso? segundo modulo? isso eh o que este campo responde

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PublicationStatus publicationStatus = PublicationStatus.DRAFT;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp
    private Instant modifiedAt;

    @OneToMany(mappedBy = "courseModule", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("position ASC")
    private final List<Lesson> lessons = new ArrayList<>();

    public CourseModule(CourseModuleCreationDto courseModuleCreationDto, Course course) {
        this.title = courseModuleCreationDto.getTitle();
        this.description = courseModuleCreationDto.getDescription();
        this.position = courseModuleCreationDto.getPosition();
        this.course = course;
        this.durationInSeconds = course.getDurationInSeconds();
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.setCourseModule(this);
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
        lesson.setCourseModule(null);
    }

}
