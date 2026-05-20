package br.edu.utfpr.inteligenteacademy.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.edu.utfpr.inteligenteacademy.model.dto.UsuarioCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.UsuarioResponseDto;
import br.edu.utfpr.inteligenteacademy.service.UsuarioService;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/usuario")
public class UsuarioController {
	private UsuarioService usuarioService;
	
	public UsuarioController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}
	
	// ----- GET -----
	@GetMapping
	public ResponseEntity<List<UsuarioResponseDto>> findAll(){
		return ResponseEntity.status(HttpStatus.OK).body(usuarioService.findAll());
	}
	
	@GetMapping("/{usuarioId}")
	public ResponseEntity<UsuarioResponseDto> findById(@PathVariable Integer usuarioId){
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
	

	// ----- DELETE -----
	
}
