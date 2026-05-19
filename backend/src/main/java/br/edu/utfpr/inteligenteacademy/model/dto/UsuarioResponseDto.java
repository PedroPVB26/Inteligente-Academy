package br.edu.utfpr.inteligenteacademy.model.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

import br.edu.utfpr.inteligenteacademy.entity.Usuario;

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
public class UsuarioResponseDto {

    private Integer id;

    private String cpf;

    private String nome;

    private String email;

    private LocalDate dataNascimento;

    private Boolean verificado;

    private Short tipoUsuario;

    private LocalDateTime createdAt;

    private LocalDateTime modifiedAt;

    public UsuarioResponseDto() {

    }

    public UsuarioResponseDto(
            Integer id,
            String cpf,
            String nome,
            String email,
            LocalDate dataNascimento,
            Boolean verificado,
            Short tipoUsuario,
            LocalDateTime createdAt,
            LocalDateTime modifiedAt
    ) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.dataNascimento = dataNascimento;
        this.verificado = verificado;
        this.tipoUsuario = tipoUsuario;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public UsuarioResponseDto(Usuario usuario) {
        this.id = usuario.getId();
        this.cpf = usuario.getCpf();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.dataNascimento = usuario.getDataNascimento();
        this.verificado = usuario.getVerificado();
        this.tipoUsuario = usuario.getTipoUsuario();
        this.createdAt = usuario.getCreatedAt();
        this.modifiedAt = usuario.getModifiedAt();
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
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

    public Short getTipoUsuario() {
        return tipoUsuario;
    }

    public void setTipoUsuario(Short tipoUsuario) {
        this.tipoUsuario = tipoUsuario;
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