package br.edu.utfpr.inteligenteacademy.model.dto.curso;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.edu.utfpr.inteligenteacademy.entity.Curso;
import br.edu.utfpr.inteligenteacademy.model.dto.etiqueta.EtiquetaResponseDto;

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
    "nome",
    "descricao",
    "duracao",
    "etiquetas",
    "createdAt",
    "modifiedAt"
})
public class CursoResponseDto {

    private Long id;

    private String nome;

    private String descricao;

    private Integer duracao;

    private List<EtiquetaResponseDto> etiquetas;
    
    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public CursoResponseDto() {

    }

//    public CursoResponseDto(Integer id, String nome, String descricao, Integer duracao, LocalDateTime createdAt, LocalDateTime modifiedAt) {
//        this.id = id;
//        this.nome = nome;
//        this.descricao = descricao;
//        this.duracao = duracao;
//        this.createdAt = createdAt;
//        this.modifiedAt = modifiedAt;
//    }

    public CursoResponseDto(Curso curso) {
        this.id = curso.getId();
        this.nome = curso.getNome();
        this.descricao = curso.getDescricao();
        this.duracao = curso.getDuracao();
        this.createdAt = curso.getCreatedAt();
        this.modifiedAt = curso.getModifiedAt();
        this.etiquetas = curso.getCursoEtiquetas()
                .stream()
                .map(cursoEtiqueta ->
                        new EtiquetaResponseDto(
                                cursoEtiqueta.getEtiqueta()
                        )
                )
                .collect(Collectors.toList());
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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

	public List<EtiquetaResponseDto> getEtiquetas() {
		return etiquetas;
	}

	public void setEtiquetas(List<EtiquetaResponseDto> etiquetas) {
		this.etiquetas = etiquetas;
	}
    
    
}