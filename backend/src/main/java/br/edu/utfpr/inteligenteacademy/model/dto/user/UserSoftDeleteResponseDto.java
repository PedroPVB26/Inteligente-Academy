package br.edu.utfpr.inteligenteacademy.model.dto.user;

import java.time.LocalDateTime;

import br.edu.utfpr.inteligenteacademy.entity.User;

public class UserSoftDeleteResponseDto {

    private Long id;

    private Boolean deleted;

    private LocalDateTime deletedAt;

	public UserSoftDeleteResponseDto(Long id, Boolean deleted, LocalDateTime deletedAt) {
		this.id = id;
		this.deleted = deleted;
		this.deletedAt = deletedAt;
	}

	public UserSoftDeleteResponseDto(User user) {
		this.id = user.getId();
		this.deleted = user.getDeleted();
		this.deletedAt = user.getDeletedAt();
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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

    
}