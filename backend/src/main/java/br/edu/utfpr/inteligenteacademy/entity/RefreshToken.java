package br.edu.utfpr.inteligenteacademy.entity;

import jakarta.persistence.*;

import java.time.Instant;

@Entity
public class RefreshToken {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @Column(nullable = false, unique = true)
    private String refreshToken;

    private Instant expiryDate;

    private boolean revoked = false;

    public RefreshToken() {}


    public RefreshToken(Usuario usuario, String refreshToken, Instant expiryDate, boolean revoked) {
        this.usuario = usuario;
        this.refreshToken = refreshToken;
        this.expiryDate = expiryDate;
        this.revoked = revoked;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Instant getExpiryDate() {
        return expiryDate;
    }

    public void setExpiryDate(Instant expiryDate) {
        this.expiryDate = expiryDate;
    }

    public boolean isRevoked() {
        return revoked;
    }

    public void setRevoked(boolean revoked) {
        this.revoked = revoked;
    }
}
