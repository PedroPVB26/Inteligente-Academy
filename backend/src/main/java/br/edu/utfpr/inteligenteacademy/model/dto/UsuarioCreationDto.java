package br.edu.utfpr.inteligenteacademy.model.dto;

import java.time.LocalDate;

import br.edu.utfpr.inteligenteacademy.entity.Usuario;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;

public class UsuarioCreationDto {

    private Integer id;

    @NotBlank(message = "cpf must not be blank")
    @Pattern(
        regexp = "^\\d{11}$",
        message = "cpf must contain exactly 11 numeric digits"
    )
    private String cpf;

    @NotBlank(message = "nome must not be blank")
    @Size(
        min = 3,
        max = 120,
        message = "nome must contain between 3 and 120 characters"
    )
    @Pattern(
        regexp = "^[A-Za-zÀ-ÿ' -]+$",
        message = "nome must contain only letters and spaces"
    )
    private String nome;

    @NotBlank(message = "email must not be blank")
    @Email(message = "email must be a valid email address")
    @Size(
        max = 150,
        message = "email must contain at most 150 characters"
    )
    private String email;

    @NotBlank(message = "senha must not be blank")
    @Size(
        min = 8,
        max = 100,
        message = "senha must contain between 8 and 100 characters"
    )
    private String senha;

    @NotNull(message = "dataNascimento must not be null")
    @Past(message = "dataNascimento must be a past date")
    private LocalDate dataNascimento;

    @NotNull(message = "verificado must not be null")
    private Boolean verificado;

    @NotNull(message = "tipoUsuario must not be null")
    private Short tipoUsuario;

    @NotNull(message = "statusExcluido must not be null")
    private Boolean statusExcluido;

    public UsuarioCreationDto() {
        
    }
    
    public UsuarioCreationDto(Usuario usuario) {
        this.id = usuario.getId();
        this.cpf = usuario.getCpf();
        this.nome = usuario.getNome();
        this.email = usuario.getEmail();
        this.senha = usuario.getSenha();
        this.dataNascimento = usuario.getDataNascimento();
        this.verificado = usuario.getVerificado();
        this.tipoUsuario = usuario.getTipoUsuario();
        this.statusExcluido = usuario.getStatusExcluido();
    }
    
    
    public UsuarioCreationDto(
            Integer id,
            String cpf,
            String nome,
            String email,
            String senha,
            LocalDate dataNascimento,
            Boolean verificado,
            Short tipoUsuario,
            Boolean statusExcluido
    ) {
        this.id = id;
        this.cpf = cpf;
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dataNascimento = dataNascimento;
        this.verificado = verificado;
        this.tipoUsuario = tipoUsuario;
        this.statusExcluido = statusExcluido;
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

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
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

    public Boolean getStatusExcluido() {
        return statusExcluido;
    }

    public void setStatusExcluido(Boolean statusExcluido) {
        this.statusExcluido = statusExcluido;
    }
}