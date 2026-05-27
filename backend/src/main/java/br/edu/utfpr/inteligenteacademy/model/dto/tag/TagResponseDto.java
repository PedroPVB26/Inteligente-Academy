package br.edu.utfpr.inteligenteacademy.model.dto.tag;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.edu.utfpr.inteligenteacademy.entity.Tag;


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

    public String getName() {
        return name;
    }


    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

}