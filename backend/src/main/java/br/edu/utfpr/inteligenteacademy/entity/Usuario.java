package br.edu.utfpr.inteligenteacademy.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.edu.utfpr.inteligenteacademy.model.dto.UsuarioCreationDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String senha;

    @Column(nullable = false)
    private LocalDate dataNascimento;

    @Column(nullable = false)
    private Boolean verificado;

    @Column(nullable = false)
    private Short tipoUsuario; // Internamente tratar como Enum (int -> Tipo) e.g ALUNO -> 1, ADMIN -> 3

    @Column(nullable = false)
    private Boolean statusExcluido;

    private LocalDateTime deletedAt;
    
    @CreationTimestamp // Preenche automaticamente quando o registro é criado.
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Atualiza automaticamente sempre que houver alteração na entidade.
    private LocalDateTime modifiedAt;

    
    public Usuario() {

	}


	public Usuario(Integer id, String cpf, String nome, String email, String senha, LocalDate dataNascimento,
			Boolean verificado, Short tipoUsuario, Boolean statusExcluido, LocalDateTime deletedAt,
			LocalDateTime createdAt, LocalDateTime modifiedAt) {
		super();
		this.id = id;
		this.cpf = cpf;
		this.nome = nome;
		this.email = email;
		this.senha = senha;
		this.dataNascimento = dataNascimento;
		this.verificado = verificado;
		this.tipoUsuario = tipoUsuario;
		this.statusExcluido = statusExcluido;
		this.deletedAt = deletedAt;
//		this.createdAt = createdAt;
//		this.modifiedAt = modifiedAt;
	}

	public Usuario(UsuarioCreationDto dto) {
	    this.cpf = dto.getCpf();
	    this.nome = dto.getNome();
	    this.email = dto.getEmail();
	    this.senha = dto.getSenha();
	    this.dataNascimento = dto.getDataNascimento();
	    this.verificado = dto.getVerificado();
	    this.tipoUsuario = dto.getTipoUsuario();
	    this.statusExcluido = dto.getStatusExcluido();
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


	public LocalDateTime getModifiedAt() {
		return modifiedAt;
	}


	public void setModifiedAt(LocalDateTime modifiedAt) {
		this.modifiedAt = modifiedAt;
	}
    
    
}