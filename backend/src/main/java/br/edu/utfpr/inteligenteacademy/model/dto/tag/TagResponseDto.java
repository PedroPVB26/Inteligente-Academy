package br.edu.utfpr.inteligenteacademy.model.dto.tag;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.edu.utfpr.inteligenteacademy.entity.Tag;

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
    "name",
    "createdAt",
    "modifiedAt"
})
public class TagResponseDto {

    private Long id;

    private String name;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public TagResponseDto() {

    }

    public TagResponseDto(Long id, String name, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public TagResponseDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.createdAt = tag.getCreatedAt();
        this.modifiedAt = tag.getModifiedAt();
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
}