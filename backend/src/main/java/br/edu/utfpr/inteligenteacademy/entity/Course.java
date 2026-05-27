package br.edu.utfpr.inteligenteacademy.entity;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.edu.utfpr.inteligenteacademy.model.dto.PublicationStatus;
import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseCreationDto;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer duration; // in hours

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PublicationStatus publicationStatus = PublicationStatus.DRAFT;

    @CreationTimestamp // Preenche automaticamente quando o registro é criado.
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Atualiza automaticamente sempre que houver alteração na entidade.
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<CourseTag> courseTags = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    @OrderBy("position ASC")
    private final List<CourseModule> courseModules = new ArrayList<>();

    public Course() {

	}

    public Course(CourseCreationDto dto){
        this.id = dto.getId();
        this.name = dto.getName();
        this.description = dto.getDescription();
        this.duration = dto.getDuration();
    }

    public void addModule(CourseModule courseModule){
        courseModules.add(courseModule);
        courseModule.setCourse(this);
    }

    public void removeModule(CourseModule courseModule){
        courseModules.remove(courseModule);
        courseModule.setCourse(null);
    }

    public void addTag(Tag tag) {
        CourseTag courseTag =new CourseTag(this, tag);
        courseTags.add(courseTag);
    }

    public void removeTag(Tag tag) {
        courseTags.removeIf(relacao -> relacao.getEtiqueta().equals(tag));
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public PublicationStatus getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(PublicationStatus publicationStatus) {
        this.publicationStatus = publicationStatus;
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

    public Set<CourseTag> getCourseTags() {
        return courseTags;
    }

    public List<CourseModule> getModules() {
        return courseModules;
    }
}
