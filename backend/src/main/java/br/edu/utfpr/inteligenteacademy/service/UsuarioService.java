package br.edu.utfpr.inteligenteacademy.service;

import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.Usuario;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.UsuarioRepository;


@Service
public class UsuarioService {
	private final UsuarioRepository usuarioRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
		this.usuarioRepository = usuarioRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional(readOnly = true)
	public List<UsuarioResponseDto> findAll(){
		List<Usuario> usuarios = usuarioRepository.findAll();
		return usuarios.stream().map(x -> new UsuarioResponseDto(x)).toList();
	}
	
	
	@Transactional(readOnly = true)
	public UsuarioResponseDto findById(Integer usuarioId) {
		Usuario usuario =
		        usuarioRepository.findById(usuarioId)
		        .orElseThrow(() ->
			        new ResourceNotFoundException(
			                "User with id "
			                + usuarioId
			                + " not found"
			        )
		        );
		return new UsuarioResponseDto(usuario);
	}
	
	@Transactional
	public UsuarioResponseDto save(UsuarioCreationDto usuarioCreationDto) {
		if(usuarioRepository.existsByCpf(usuarioCreationDto.getCpf())) {
			throw new DatabaseException("CPF already exists in the database");
		}
		
		if (usuarioRepository.existsByEmail(usuarioCreationDto.getEmail())) {
	        throw new DatabaseException("Email already exists in the database");
	    }
		
		Usuario usuario = new Usuario(usuarioCreationDto);
		
		String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
		
		usuario.setSenha(senhaCriptografada);
		usuario.setVerificado(false);
		usuario.setStatusExcluido(false);
		
		Usuario usuarioSalvo = usuarioRepository.save(usuario);
		
		return new UsuarioResponseDto(usuarioSalvo);
	}
}
