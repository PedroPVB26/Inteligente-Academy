package br.edu.utfpr.inteligenteacademy.entity;

import java.time.Instant;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;

@Entity
public class TokenVerificacaoEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @OneToOne
    @JoinColumn(name = "usuario_id", nullable = false, unique = true)
    private Usuario usuario;

    @Column(nullable = false)
    private Instant expiracao;

    private boolean usado = false;

    public TokenVerificacaoEmail() {}
    
	public TokenVerificacaoEmail(String token, Usuario usuario, Instant expiracao, boolean usado) {
		this.token = token;
		this.usuario = usuario;
		this.expiracao = expiracao;
		this.usado = usado;
	}

	public Long getId() {
		return id;
	}

	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public Usuario getUsuario() {
		return usuario;
	}

	public void setUsuario(Usuario usuario) {
		this.usuario = usuario;
	}

	public Instant getExpiracao() {
		return expiracao;
	}

	public void setExpiracao(Instant expiracao) {
		this.expiracao = expiracao;
	}

	public boolean isUsado() {
		return usado;
	}

	public void setUsado(boolean usado) {
		this.usado = usado;
	}
    
    
}