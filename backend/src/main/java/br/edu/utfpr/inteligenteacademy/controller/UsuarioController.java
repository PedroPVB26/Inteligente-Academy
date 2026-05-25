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

import br.edu.utfpr.inteligenteacademy.entity.Usuario;
import br.edu.utfpr.inteligenteacademy.model.dto.ChangePasswordRequestDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioSoftDeleteResponseDto;
import br.edu.utfpr.inteligenteacademy.service.PasswordService;
import br.edu.utfpr.inteligenteacademy.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {

	private UsuarioService usuarioService;
	private PasswordService passwordService;
	
	public UsuarioController(UsuarioService usuarioService, PasswordService passwordService) {
		this.usuarioService = usuarioService;
		this.passwordService = passwordService;
	}
	
	// ----- GET -----
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDto>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
	}
	
	@GetMapping("/{usuarioId}")
	public ResponseEntity<UsuarioResponseDto> findById(@PathVariable Long usuarioId){
		UsuarioResponseDto usuarioResponseDto = usuarioService.findById(usuarioId);
		return ResponseEntity.status(HttpStatus.OK).body(usuarioResponseDto);
	}
	
	// ----- POST -----
	@PostMapping
	public ResponseEntity<UsuarioResponseDto> save(@Valid @RequestBody UsuarioCreationDto usuarioCreationDto){
		UsuarioResponseDto usuarioSalvo = usuarioService.save(usuarioCreationDto);
		return ResponseEntity.status(HttpStatus.CREATED).body(usuarioSalvo);
	}
	
	// ----- PUT -----
	
	// Logado alterando senha
	@PutMapping("/password")
	public ResponseEntity<Void> changePassword(
			@AuthenticationPrincipal Usuario usuario,
			@Valid @RequestBody ChangePasswordRequestDto changePasswordRequestDto){
		
		passwordService.changePassword(usuario.getEmail(), changePasswordRequestDto);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
	

	// ----- DELETE -----
	@DeleteMapping("/{usuarioId}")
	public ResponseEntity<UsuarioSoftDeleteResponseDto> softDelete(@PathVariable Long usuarioId){
		UsuarioSoftDeleteResponseDto usuarioExcluido = usuarioService.softDelete(usuarioId);
		return ResponseEntity.status(HttpStatus.OK).body(usuarioExcluido);
	}
}
