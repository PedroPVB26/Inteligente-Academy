package br.edu.utfpr.inteligenteacademy.model.dto.user;

import java.time.LocalDateTime;

import br.edu.utfpr.inteligenteacademy.entity.User;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSoftDeleteResponseDto {
    private Long id;
    private Boolean deleted;
    private LocalDateTime deletedAt;

	public UserSoftDeleteResponseDto(User user) {
		this.id = user.getId();
		this.deleted = user.getDeleted();
		this.deletedAt = user.getDeletedAt();
	}
}