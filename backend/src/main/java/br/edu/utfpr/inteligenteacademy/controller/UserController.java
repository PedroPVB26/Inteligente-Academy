package br.edu.utfpr.inteligenteacademy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.inteligenteacademy.entity.User;
import br.edu.utfpr.inteligenteacademy.model.dto.password.ChangePasswordRequestDto;
import br.edu.utfpr.inteligenteacademy.model.dto.user.UserCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.user.UserResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.user.UserSoftDeleteResponseDto;
import br.edu.utfpr.inteligenteacademy.service.PasswordService;
import br.edu.utfpr.inteligenteacademy.service.UserService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/users")
public class UserController {

	private UserService userService;
	private PasswordService passwordService;
	
	public UserController(UserService userService, PasswordService passwordService) {
		this.userService = userService;
		this.passwordService = passwordService;
	}
	
	// ----- GET -----
	@GetMapping
	public ResponseEntity<List<UserResponseDto>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(userService.findAll());
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<UserResponseDto> findById(@PathVariable Long userId){
		UserResponseDto userResponseDto = userService.findById(userId);
		return ResponseEntity.status(HttpStatus.OK).body(userResponseDto);
	}
	
	// ----- POST -----
	@PostMapping
	public ResponseEntity<UserResponseDto> save(@Valid @RequestBody UserCreationDto userCreationDto){
		UserResponseDto savedUser = userService.save(userCreationDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(savedUser);
	}
	
	// ----- PUT -----
	
	// Logado alterando senha
	@PutMapping("/password")
	public ResponseEntity<Void> changePassword(
			@AuthenticationPrincipal User user,
			@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto){
		
		passwordService.changePassword(user.getEmail(), changePasswordRequestDto);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	

	// ----- DELETE -----
	@DeleteMapping("/{userId}")
	public ResponseEntity<UserSoftDeleteResponseDto> softDelete(@PathVariable Long userId){
		UserSoftDeleteResponseDto deletedUser = userService.softDelete(userId);
		return ResponseEntity.status(HttpStatus.OK).body(deletedUser);
	}
}
