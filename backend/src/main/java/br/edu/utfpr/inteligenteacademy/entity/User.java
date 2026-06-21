package br.edu.utfpr.inteligenteacademy.entity;

import java.time.Instant;
import java.time.LocalDate;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.jspecify.annotations.Nullable;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import br.edu.utfpr.inteligenteacademy.model.dto.user.UserRole;
import br.edu.utfpr.inteligenteacademy.model.dto.user.UserCreationDto;

@Entity
@Table(name = "users") // user eh uma palavra reservada no PostgreSQL
@Getter
@Setter
@NoArgsConstructor
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
    private UserRole role;

    @Column(nullable = false)
    private Boolean deleted;

    private Instant passwordChangedAt;
    
	private Instant deletedAt;

	@CreationTimestamp
	@Column(nullable = false, updatable = false)
	private Instant createdAt;

	@UpdateTimestamp
	private Instant modifiedAt;

	@OneToMany(
			mappedBy = "user",
			cascade = CascadeType.ALL,
			orphanRemoval = true
	)
	private List<Enrollment> enrollments = new ArrayList<>();

	public User(UserCreationDto dto) {
	    this.cpf = dto.getCpf();
	    this.fullName = dto.getName();
	    this.email = dto.getEmail();
	    this.password = dto.getPassword();
	    this.birthDate = dto.getBirthDate();
	    this.role = dto.getUserRole();
	    this.deleted = false;
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