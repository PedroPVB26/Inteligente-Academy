package br.edu.utfpr.inteligenteacademy.model.dto;

import java.time.LocalDate;

import br.edu.utfpr.inteligenteacademy.entity.Etiqueta;
import br.edu.utfpr.inteligenteacademy.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class EtiquetaCreationDto {

    private Integer id;

    @NotBlank(message = "nome must not be blank")
    @Size(
        min = 3,
        max = 120,
        message = "nome must contain between 3 and 120 characters"
    )
    private String nome;


    public EtiquetaCreationDto() {
        
    }
    
    public EtiquetaCreationDto(Etiqueta etiqueta) {
        this.id = etiqueta.getId();
        this.nome = etiqueta.getNome();
    }

    public EtiquetaCreationDto(Integer id, String nome) {
        this.id = id;
        this.nome = nome;
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
}