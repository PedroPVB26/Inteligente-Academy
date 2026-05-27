package br.edu.utfpr.inteligenteacademy.model.dto.user;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonPropertyOrder;

import br.edu.utfpr.inteligenteacademy.entity.User;

/*
 * DTO responsável por representar os dados retornados pela API
 * relacionados ao usuário.
 *
 * Sua finalidade é desacoplar a entidade Usuario da camada de
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
        "cpf",
        "name",
        "email",
        "birthDate",
        "verified",
        "userRole",
        "createdAt",
        "modifiedAt"
})
public class UserResponseDto {

    private Long id;

    private String cpf;

    private String name;

    private String email;

    private LocalDate birthDate;

    private Boolean verified;

    private UserRole userRole;

    private Boolean deleted;

    private LocalDateTime deletedAt;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public UserResponseDto() {

    }

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

    public LocalDateTime getModifiedAt() {
        return modifiedAt;
    }

    public void setModifiedAt(LocalDateTime modifiedAt) {
        this.modifiedAt = modifiedAt;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Boolean getDeleted() {
        return deleted;
    }

    public void setDeleted(Boolean deleted) {
        this.deleted = deleted;
    }

    public UserRole getUserRole() {
        return userRole;
    }

    public void setUserRole(UserRole userRole) {
        this.userRole = userRole;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public LocalDate getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(LocalDate birthDate) {
        this.birthDate = birthDate;
    }

    public Boolean getVerified() {
        return verified;
    }

    public void setVerified(Boolean verified) {
        this.verified = verified;
    }
}