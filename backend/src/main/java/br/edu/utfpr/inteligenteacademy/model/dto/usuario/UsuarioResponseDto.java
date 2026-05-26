package br.edu.utfpr.inteligenteacademy.model.dto.usuario;

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
        "nome",
        "email",
        "dataNascimento",
        "verificado",
        "tipoUsuario",
        "createdAt",
        "modifiedAt"
})
public class UsuarioResponseDto {

    private Long id;

    private String cpf;

    private String nome;

    private String email;

    private LocalDate dataNascimento;

    private Boolean verificado;

    private UserRole userRole;

    private Boolean statusExcluido;

    private LocalDateTime deletedAt;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public UsuarioResponseDto() {

    }

//    public UsuarioResponseDto(
//            Long id,
//            String cpf,
//            String nome,
//            String email,
//            LocalDate dataNascimento,
//            Boolean verificado,
//            TipoUsuario tipoUsuario,
//            LocalDateTime createdAt,
//            LocalDateTime modifiedAt,
//            boolean statusExcluido,
//            LocalDateTime deletedAt) {
//        this.id = id;
//        this.cpf = cpf;
//        this.nome = nome;
//        this.email = email;
//        this.dataNascimento = dataNascimento;
//        this.verificado = verificado;
//        this.tipoUsuario = tipoUsuario;
//        this.createdAt = createdAt;
//        this.modifiedAt = modifiedAt;
//        this.statusExcluido = statusExcluido;
//        this.deletedAt = deletedAt;
//    }

    public UsuarioResponseDto(User user) {
        this.id = user.getId();
        this.cpf = user.getCpf();
        this.nome = user.getFullName();
        this.email = user.getEmail();
        this.dataNascimento = user.getBirthDate();
        this.verificado = user.getVerified();
        this.userRole = user.getRole();
        this.createdAt = user.getCreatedAt();
        this.modifiedAt = user.getModifiedAt();
        this.statusExcluido = user.getDeleted();
        this.deletedAt = user.getDeletedAt();
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDate getDataNascimento() {
        return dataNascimento;
    }

    public void setDataNascimento(LocalDate dataNascimento) {
        this.dataNascimento = dataNascimento;
    }

    public Boolean getVerificado() {
        return verificado;
    }

    public void setVerificado(Boolean verificado) {
        this.verificado = verificado;
    }

    public UserRole getTipoUsuario() {
        return userRole;
    }

    public void setTipoUsuario(UserRole userRole) {
        this.userRole = userRole;
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

    public Boolean getStatusExcluido() {
        return statusExcluido;
    }

    public void setStatusExcluido(Boolean statusExcluido) {
        this.statusExcluido = statusExcluido;
    }

    public LocalDateTime getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(LocalDateTime deletedAt) {
        this.deletedAt = deletedAt;
    }

}