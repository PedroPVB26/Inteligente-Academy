package br.edu.utfpr.inteligenteacademy.entity;

import br.edu.utfpr.inteligenteacademy.model.dto.PublicationStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.module.CourseModuleCreationDto;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
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

    @Column(nullable = false, unique = true)
    private Integer position; // primeiro modulo do curso? segundo modulo? isso eh o que este campo responde

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PublicationStatus publicationStatus = PublicationStatus.DRAFT;

    @CreationTimestamp
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "courseModule", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private final List<Lesson> lessons = new ArrayList<>();

    public CourseModule() {
    }

    public CourseModule(CourseModuleCreationDto courseModuleCreationDto, Course course) {
        this.title = courseModuleCreationDto.getTitle();
        this.description = courseModuleCreationDto.getDescription();
        this.position = courseModuleCreationDto.getPosition();
        this.course = course;
    }

    public void addLesson(Lesson lesson) {
        lessons.add(lesson);
        lesson.setModule(this);
    }

    public void removeLesson(Lesson lesson) {
        lessons.remove(lesson);
        lesson.setModule(null);
    }

    public PublicationStatus getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(PublicationStatus publicationStatus) {
        this.publicationStatus = publicationStatus;
    }

    public List<Lesson> getLessons() {
        return lessons;
    }

    public Long getId() {
        return id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPosition() {
        return position;
    }

    public void setPosition(Integer position) {
        this.position = position;
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


}
