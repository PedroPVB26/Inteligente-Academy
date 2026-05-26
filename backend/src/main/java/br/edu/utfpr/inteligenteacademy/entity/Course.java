package br.edu.utfpr.inteligenteacademy.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.edu.utfpr.inteligenteacademy.model.dto.curso.CursoCreationDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String description;

    @Column(nullable = false)
    private Integer duration; // in hours

    @CreationTimestamp // Preenche automaticamente quando o registro é criado.
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Atualiza automaticamente sempre que houver alteração na entidade.
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CourseTag> courseTags = new HashSet<>();
    
    public Course() {

	}

    public Course(Long id, String name, String description, Integer duration, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.duration = duration;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Course(CursoCreationDto dto){
        this.id = dto.getId();
        this.name = dto.getNome();
        this.description = dto.getDescricao();
        this.duration = dto.getDuracao();
    }

    public void adicionarEtiqueta(Tag tag) {

        CourseTag courseTag =new CourseTag(this, tag);

        courseTags.add(courseTag);
    }

    public void removerEtiqueta(Tag tag) {

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



}
