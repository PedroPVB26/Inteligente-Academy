package br.edu.utfpr.inteligenteacademy.model.dto.course;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CourseCreationDto {

    private Long id;

    @NotBlank(message = "name must not be blank")
    @Size(
        min = 3,
        max = 120,
        message = "name must contain between 3 and 120 characters"
    )
    private String name;

    @NotBlank(message = "description must not be blank")
    @Size(
        min = 3,
        max = 255,
        message = "description must contain between 3 and 255 characters"
    )
    private String description;

    @NotBlank(message = "duration must not be blank")
    private Integer duration;

    private List<@Positive Long> tagsIds;
    
    public CourseCreationDto() {
        
    }
    
//    public CursoCreationDto(Curso curso) {
//        this.id = curso.getId();
//        this.nome = curso.getNome();
//        this.descricao = curso.getDescricao();
//        this.duracao = curso.getDuracao();
//    }

//    public CursoCreationDto(Long id, String nome, String descricao, Integer duracao, List<Long> etiquetasIds) {
//        this.id = id;
//        this.nome = nome;
//        this.descricao = descricao;
//        this.duracao = duracao;
//        this.etiquetasIds = etiquetasIds;
//    }

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

	public List<Long> getTagsIds() {
		return tagsIds;
	}

	public void setTagsIds(List<Long> tagsIds) {
		this.tagsIds = tagsIds;
	}
    
    
}