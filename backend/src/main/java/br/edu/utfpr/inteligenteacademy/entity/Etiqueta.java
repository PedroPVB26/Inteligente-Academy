package br.edu.utfpr.inteligenteacademy.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.edu.utfpr.inteligenteacademy.model.dto.etiqueta.EtiquetaCreationDto;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Etiqueta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @CreationTimestamp // Preenche automaticamente quando o registro é criado.
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Atualiza automaticamente sempre que houver alteração na entidade.
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "etiqueta")
    private Set<CursoEtiqueta> cursoEtiquetas = new HashSet<>();
    
    public Etiqueta() {

	}

    public Etiqueta(String nome, LocalDateTime createdAt) {
        this.nome = nome;
    }

    public Etiqueta(EtiquetaCreationDto dto){   
        this.nome = dto.getNome();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
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
    
    public Set<CursoEtiqueta> getCursoEtiquetas() {
        return cursoEtiquetas;
    }

}
