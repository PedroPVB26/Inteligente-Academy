package br.edu.utfpr.inteligenteacademy.model.dto.tag;

import br.edu.utfpr.inteligenteacademy.entity.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class TagCreationDto {

    @NotBlank(message = "name must not be blank")
    @Size(
        min = 2,
        max = 120,
        message = "name must contain between 2 and 120 characters"
    )
    private String name;

    public TagCreationDto() {

    }

    public TagCreationDto(Tag tag) {
        this.name = tag.getName();
    }

    public TagCreationDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}