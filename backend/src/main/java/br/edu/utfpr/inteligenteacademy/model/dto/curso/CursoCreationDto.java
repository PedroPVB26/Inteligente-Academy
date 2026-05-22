package br.edu.utfpr.inteligenteacademy.model.dto.curso;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public class CursoCreationDto {

    private Integer id;

    @NotBlank(message = "nome must not be blank")
    @Size(
        min = 3,
        max = 120,
        message = "nome must contain between 3 and 120 characters"
    )
    private String nome;

    @NotBlank(message = "descricao must not be blank")
    @Size(
        min = 3,
        max = 255,
        message = "descricao must contain between 3 and 255 characters"
    )
    private String descricao;

    @NotBlank(message = "duracao must not be blank")
    private Integer duracao;

    private List<@Positive Integer> etiquetasIds;
    
    public CursoCreationDto() {
        
    }
    
//    public CursoCreationDto(Curso curso) {
//        this.id = curso.getId();
//        this.nome = curso.getNome();
//        this.descricao = curso.getDescricao();
//        this.duracao = curso.getDuracao();
//    }

    public CursoCreationDto(Integer id, String nome, String descricao, Integer duracao, List<Integer> etiquetasIds) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.duracao = duracao;
        this.etiquetasIds = etiquetasIds;
    }

	public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
    }

	public List<Integer> getEtiquetasIds() {
		return etiquetasIds;
	}

	public void setEtiquetasIds(List<Integer> etiquetasIds) {
		this.etiquetasIds = etiquetasIds;
	}
    
    
}