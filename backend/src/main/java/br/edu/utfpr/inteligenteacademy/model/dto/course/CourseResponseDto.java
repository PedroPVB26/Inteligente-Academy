package br.edu.utfpr.inteligenteacademy.model.dto.course;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import br.edu.utfpr.inteligenteacademy.model.PublicationStatus;
import br.edu.utfpr.inteligenteacademy.model.dto.module.CourseModuleResponseDto;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.edu.utfpr.inteligenteacademy.entity.Course;
import br.edu.utfpr.inteligenteacademy.model.dto.tag.TagResponseDto;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
@Getter
@Setter
@NoArgsConstructor
public class CourseResponseDto {
    private Long id;
    private String name;
    private String description;
    private Long duration;
    private PublicationStatus  publicationStatus;
    private List<TagResponseDto> tags;
    private List<CourseModuleResponseDto> courseModules;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public CourseResponseDto(Course course) {
        this.id = course.getId();
        this.name = course.getName();
        this.description = course.getDescription();
        this.duration = course.getDurationInMinutes();
        this.createdAt = course.getCreatedAt();
        this.modifiedAt = course.getModifiedAt();
        this.tags = course.getCourseTags()
                .stream()
                .map(cursoEtiqueta ->
                        new TagResponseDto(
                                cursoEtiqueta.getTag()
                        )
                )
                .collect(Collectors.toList());
        this.publicationStatus = course.getPublicationStatus();
        this.courseModules = course.getCourseModules()
                .stream()
                .map(CourseModuleResponseDto::new)
                .collect(Collectors.toList());
    }

}