package br.edu.utfpr.inteligenteacademy.model.dto.user;

import java.time.LocalDate;
import java.time.Instant;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.edu.utfpr.inteligenteacademy.entity.User;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@JsonPropertyOrder({
        "id",
        "cpf",
        "name",
        "email",
        "birthDate",
        "verified",
        "userRole",
        "createdAt",
        "modifiedAt"
})
@Getter
@Setter
@NoArgsConstructor
public class UserResponseDto {

    private Long id;
    private String cpf;
    private String name;
    private String email;
    private LocalDate birthDate;
    private Boolean verified;
    private UserRole userRole;
    private Boolean deleted;
    private Instant deletedAt;
    private Instant createdAt;
    private Instant modifiedAt;

    public UserResponseDto(User user) {
        this.id = user.getId();
        this.cpf = user.getCpf();
        this.name = user.getFullName();
        this.email = user.getEmail();
        this.birthDate = user.getBirthDate();
        this.verified = user.getVerified();
        this.userRole = user.getRole();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
        this.deleted = user.getDeleted();
        this.deletedAt = user.getDeletedAt();
    }
}