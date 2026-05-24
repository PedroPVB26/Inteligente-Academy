package br.edu.utfpr.inteligenteacademy.entity;

import java.time.Instant;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;

@Entity
public class PasswordResetToken {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @ManyToOne
    private Usuario usuario;

    private String token;       // hash do token
    private Instant expiresAt;
    private boolean used;
    
    public PasswordResetToken() {}

	public PasswordResetToken(Usuario usuario, String token, Instant expiresAt, boolean used) {
		this.usuario = usuario;
		this.token = token;
		this.expiresAt = expiresAt;
		this.used = used;
	}

	public String getId() {
		return id;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Instant getExpiresAt() {
		return expiresAt;
	}

	public void setExpiresAt(Instant expiresAt) {
		this.expiresAt = expiresAt;
	}

	public boolean isUsed() {
		return used;
	}

	public void setUsed(boolean used) {
		this.used = used;
	}
    
	
    
}
