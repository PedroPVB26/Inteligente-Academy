package br.edu.utfpr.inteligenteacademy.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;

@Entity
public class TokenVerificacaoEmail {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String token;

    @OneToOne
    private Usuario usuario;

    private LocalDateTime expiracao;

    private boolean usado = false;

    public TokenVerificacaoEmail() {}
    
	public TokenVerificacaoEmail(String token, Usuario usuario, LocalDateTime expiracao, boolean usado) {
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

	public LocalDateTime getExpiracao() {
		return expiracao;
	}

	public void setExpiracao(LocalDateTime expiracao) {
		this.expiracao = expiracao;
	}

	public boolean isUsado() {
		return usado;
	}

	public void setUsado(boolean usado) {
		this.usado = usado;
	}
    
    
}