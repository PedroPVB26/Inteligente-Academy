package br.edu.utfpr.inteligenteacademy.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UserRole;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioCreationDto;

@Entity
@Table(name = "users") // user eh uma palavra reservada no PostgreSQL
public class User implements UserDetails{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String fullName;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private LocalDate birthDate;

    @Column(nullable = false)
    private Boolean verified;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private UserRole role; // Internamente tratar como Enum (int -> Tipo) e.g ALUNO -> 1, ADMIN -> 3

    @Column(nullable = false)
    private Boolean deleted;

    private Instant passwordChangedAt;
    
    private LocalDateTime deletedAt;
    
    @CreationTimestamp // Preenche automaticamente quando o registro é criado.
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Atualiza automaticamente sempre que houver alteração na entidade.
    private LocalDateTime modifiedAt;

    
    public User() {

	}


	public User(
			String cpf, 
			String fullName,
			String email, 
			String password,
			LocalDate birthDate,
			Boolean verified,
			UserRole role,
			Boolean deleted,
			LocalDateTime deletedAt,
			Instant passwordChangedAt) {
		this.cpf = cpf;
		this.fullName = fullName;
		this.email = email;
		this.password = password;
		this.birthDate = birthDate;
		this.verified = verified;
		this.role = role;
		this.deleted = deleted;
		this.passwordChangedAt = passwordChangedAt;
	}

	public User(UsuarioCreationDto dto) {
	    this.cpf = dto.getCpf();
	    this.fullName = dto.getNome();
	    this.email = dto.getEmail();
	    this.password = dto.getSenha();
	    this.birthDate = dto.getDataNascimento();
	    this.role = dto.getTipoUsuario();
	    this.deleted = false;
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


	public String getFullName() {
		return fullName;
	}


	public void setFullName(String fullName) {
		this.fullName = fullName;
	}


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}


	public void setPassword(String password) {
		this.password = password;
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


	public UserRole getRole() {
		return role;
	}


	public void setRole(UserRole role) {
		this.role = role;
	}


	public Boolean getDeleted() {
		return deleted;
	}


	public void setDeleted(Boolean deleted) {
		this.deleted = deleted;
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

	public Instant getPasswordChangedAt() {
		return passwordChangedAt;
	}


	public void setPasswordChangedAt(Instant passwordChangedAt) {
		this.passwordChangedAt = passwordChangedAt;
	}


	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return List.of(new SimpleGrantedAuthority("ROLE_" + role.name()));
	}


	@Override
	public @Nullable String getPassword() {
		return password;
	}


	@Override
	public String getUsername() {
		return email;
	}
    
	@Override
	public boolean isAccountNonLocked() {
	    return !deleted;
	}
    
	@Override
	public boolean isEnabled() {
	    return verified;
	}
	
	@Override
	public boolean isAccountNonExpired() {
	    return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
	    return true;
	}
}