package br.edu.utfpr.inteligenteacademy.model.dto.etiqueta;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.edu.utfpr.inteligenteacademy.entity.Etiqueta;

/*
 * DTO responsável por representar os dados retornados pela API
 * relacionados à etiqueta.
 *
 * Sua finalidade é desacoplar a entidade Etiqueta da camada de
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
    "createdAt",
    "modifiedAt"
})
public class EtiquetaResponseDto {

    private Integer id;

    private String nome;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public EtiquetaResponseDto() {

    }

    public EtiquetaResponseDto(Integer id, String nome, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.nome = nome;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public EtiquetaResponseDto(Etiqueta etiqueta) {
        this.id = etiqueta.getId();
        this.nome = etiqueta.getNome();
        this.createdAt = etiqueta.getCreatedAt();
        this.modifiedAt = etiqueta.getModifiedAt();
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