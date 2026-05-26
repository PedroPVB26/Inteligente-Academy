package br.edu.utfpr.inteligenteacademy.model.dto.usuario;

import java.time.LocalDateTime;

import br.edu.utfpr.inteligenteacademy.entity.User;

public class UsuarioSoftDeleteResponseDto {

    private Long id;

    private Boolean statusExcluido;

    private LocalDateTime deletedAt;

	public UsuarioSoftDeleteResponseDto(Long id, Boolean statusExcluido, LocalDateTime deletedAt) {
		this.id = id;
		this.statusExcluido = statusExcluido;
		this.deletedAt = deletedAt;
	}

	public UsuarioSoftDeleteResponseDto(User user) {
		this.id = user.getId();
		this.statusExcluido = user.getDeleted();
		this.deletedAt = user.getDeletedAt();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

    
}