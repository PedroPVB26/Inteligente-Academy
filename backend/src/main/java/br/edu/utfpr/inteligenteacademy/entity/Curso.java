package br.edu.utfpr.inteligenteacademy.entity;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import br.edu.utfpr.inteligenteacademy.model.dto.curso.CursoCreationDto;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;

@Entity
public class Curso {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = true, columnDefinition = "TEXT")
    private String descricao;

    @Column(nullable = false)
    private Integer duracao; // Em horas

    @CreationTimestamp // Preenche automaticamente quando o registro é criado.
    @Column(nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @UpdateTimestamp // Atualiza automaticamente sempre que houver alteração na entidade.
    private LocalDateTime modifiedAt;

    @OneToMany(mappedBy = "curso", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<CursoEtiqueta> cursoEtiquetas = new HashSet<>();
    
    public Curso() {

	}

    public Curso(Integer id, String nome, String descricao, Integer duracao, LocalDateTime createdAt, LocalDateTime modifiedAt) {
        this.id = id;
        this.nome = nome;
        this.descricao = descricao;
        this.duracao = duracao;
        this.createdAt = createdAt;
        this.modifiedAt = modifiedAt;
    }

    public Curso(CursoCreationDto dto){ 
        this.id = dto.getId();
        this.nome = dto.getNome();
        this.descricao = dto.getDescricao();
        this.duracao = dto.getDuracao();
    }

    public void adicionarEtiqueta(Etiqueta etiqueta) {

        CursoEtiqueta cursoEtiqueta =new CursoEtiqueta(this, etiqueta);

        cursoEtiquetas.add(cursoEtiqueta);
    }

    public void removerEtiqueta(Etiqueta etiqueta) {

        cursoEtiquetas.removeIf(relacao -> relacao.getEtiqueta().equals(etiqueta));
    }
    
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public Integer getDuracao() {
        return duracao;
    }

    public void setDuracao(Integer duracao) {
        this.duracao = duracao;
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
