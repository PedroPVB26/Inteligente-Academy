package br.edu.utfpr.inteligenteacademy.model.dto.tag;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.edu.utfpr.inteligenteacademy.entity.Tag;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@JsonPropertyOrder({
    "id",
    "name",
    "createdAt",
    "modifiedAt"
})
@Getter
@Setter
@NoArgsConstructor
public class TagResponseDto {
    private Long id;
    private String name;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;

    public TagResponseDto(Tag tag) {
        this.id = tag.getId();
        this.name = tag.getName();
        this.createdAt = tag.getCreatedAt();
        this.modifiedAt = tag.getModifiedAt();
    }
}