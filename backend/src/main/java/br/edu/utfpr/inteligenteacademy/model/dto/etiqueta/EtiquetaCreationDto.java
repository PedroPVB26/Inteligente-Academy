package br.edu.utfpr.inteligenteacademy.model.dto.etiqueta;

import br.edu.utfpr.inteligenteacademy.entity.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class EtiquetaCreationDto {

    @NotBlank(message = "nome must not be blank")
    @Size(
        min = 3,
        max = 120,
        message = "nome must contain between 3 and 120 characters"
    )
    private String nome;

    public EtiquetaCreationDto() {

    }

    public EtiquetaCreationDto(Tag tag) {
        this.nome = tag.getName();
    }

    public EtiquetaCreationDto(String nome) {
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }
}