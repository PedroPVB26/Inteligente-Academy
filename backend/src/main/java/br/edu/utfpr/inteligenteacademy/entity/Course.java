package br.edu.utfpr.inteligenteacademy.entity;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.edu.utfpr.inteligenteacademy.model.dto.course.CourseCreationDto;

@Entity
@Getter
@Setter
@NoArgsConstructor
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Long durationInSeconds = 0L; // in seconds. É calculado automaticamente a partir das suas aulas

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private PublicationStatus publicationStatus = PublicationStatus.DRAFT;

    @CreationTimestamp // Preenche automaticamente quando o registro é criado.
    @Column(nullable = false, updatable = false)
    private Instant createdAt;

    @UpdateTimestamp // Atualiza automaticamente sempre que houver alteração na entidade.
    private Instant modifiedAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<CourseTag> courseTags = new HashSet<>();

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
    @OrderBy("position ASC")
    private final List<CourseModule> courseModules = new ArrayList<>();


    public Course(CourseCreationDto dto){
        this.name = dto.getName();
        this.description = dto.getDescription();
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
        courseTags.removeIf(relacao -> relacao.getTag().equals(tag));
    }

}
