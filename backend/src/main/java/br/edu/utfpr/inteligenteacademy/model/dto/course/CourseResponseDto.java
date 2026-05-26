package br.edu.utfpr.inteligenteacademy.model.dto.course;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.utfpr.inteligenteacademy.model.dto.PublicationStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.module.CourseModuleResponseDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.edu.utfpr.inteligenteacademy.entity.Course;
import br.edu.utfpr.inteligenteacademy.model.dto.tag.TagResponseDto;

/*
 * DTO responsável por representar os dados retornados pela API
 * relacionados à curso.
 *
 * Sua finalidade é desacoplar a entidade Curso da camada de
 * apresentação, evitando a exposição de dados sensíveis e permitindo
 * maior controle sobre as informações retornadas ao cliente.
 *
 * Esta classe contribui para:
 * - segurança da aplicação;
 * - padronização das respostas da API;
 * - encapsulamento;
 * - manutenção e escalabilidade da arquitetura.
 */
@JsonPropertyOrder({
    "id",
    "name",
    "description",
    "duration",
    "tags",
    "publicationStatus",
    "courseModules",
    "createdAt",
    "modifiedAt"
})
public class CourseResponseDto {

    private Long id;

    private String name;

    private String description;

    private Integer duration;

    private PublicationStatus  publicationStatus;

    private List<TagResponseDto> tags;

    private List<CourseModuleResponseDto> courseModules;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;



    public CourseResponseDto() {

    }

    public CourseResponseDto(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.description = course.getDescription();
        this.duration = course.getDuration();
        this.createdAt = course.getCreatedAt();
        this.modifiedAt = course.getModifiedAt();
        this.tags = course.getCourseTags()
                .stream()
                .map(cursoEtiqueta ->
                        new TagResponseDto(
                                cursoEtiqueta.getEtiqueta()
                        )
                )
                .collect(Collectors.toList());
        this.publicationStatus = course.getPublicationStatus();
        this.courseModules = course.getModules()
                .stream()
                .map(CourseModuleResponseDto::new)
                .collect(Collectors.toList());
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

	public List<TagResponseDto> getTags() {
		return tags;
	}

	public void setTags(List<TagResponseDto> tags) {
		this.tags = tags;
	}

    public PublicationStatus getPublicationStatus() {
        return publicationStatus;
    }

    public void setPublicationStatus(PublicationStatus publicationStatus) {
        this.publicationStatus = publicationStatus;
    }

    public List<CourseModuleResponseDto> getCourseModules() {
        return courseModules;
    }

    public void setCourseModules(List<CourseModuleResponseDto> courseModules) {
        this.courseModules = courseModules;
    }
}