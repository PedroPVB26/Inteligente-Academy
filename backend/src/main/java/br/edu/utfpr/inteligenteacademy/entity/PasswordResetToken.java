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
    private User user;

    private String token;       // hash do token
    private Instant expiresAt;
    private boolean used;
    
    public PasswordResetToken() {}

//	public PasswordResetToken(User user, String token, Instant expiresAt, boolean used) {
//		this.user = user;
//		this.token = token;
//		this.expiresAt = expiresAt;
//		this.used = used;
//	}

	public String getId() {
		return id;
	}

	public User getUsuario() {
		return user;
	}

	public void setUsuario(User user) {
		this.user = user;
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
