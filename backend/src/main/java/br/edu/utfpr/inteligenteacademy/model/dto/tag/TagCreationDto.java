package br.edu.utfpr.inteligenteacademy.model.dto.tag;

import br.edu.utfpr.inteligenteacademy.entity.Tag;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class TagCreationDto {

    @NotBlank(message = "name must not be blank")
    @Size(
        min = 2,
        max = 120,
        message = "name must contain between 2 and 120 characters"
    )
    private String name;
}