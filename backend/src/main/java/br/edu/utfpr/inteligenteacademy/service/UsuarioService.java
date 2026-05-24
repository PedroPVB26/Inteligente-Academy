package br.edu.utfpr.inteligenteacademy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.Usuario;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.exception.auth.InvalidCredentialsException;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioSoftDeleteResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.UserRepository;


@Service
public class UsuarioService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UsuarioService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional(readOnly = true)
	public List<UsuarioResponseDto> findAll(){
		List<Usuario> usuarios = userRepository.findAll();
		return usuarios.stream().map(x -> new UsuarioResponseDto(x)).toList();
	}
	
	
	@Transactional(readOnly = true)
	public Usuario findEntityByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
		
	}
	
	@Transactional(readOnly = true)
	public UsuarioResponseDto findById(Integer usuarioId) {
		Usuario usuario =
		        userRepository.findById(usuarioId)
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
		if(userRepository.existsByCpf(usuarioCreationDto.getCpf())) {
			throw new DatabaseException("CPF already exists in the database");
		}
		
		if (userRepository.existsByEmail(usuarioCreationDto.getEmail())) {
	        throw new DatabaseException("Email already exists in the database");
	    }
		
		Usuario usuario = new Usuario(usuarioCreationDto);
		
		String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
		
		usuario.setSenha(senhaCriptografada);
		usuario.setVerificado(false);
		usuario.setStatusExcluido(false);
		
		Usuario usuarioSalvo = userRepository.save(usuario);
		
		return new UsuarioResponseDto(usuarioSalvo);
	}
	
	@Transactional
	public Usuario register(UsuarioCreationDto usuarioCreationDto) {
		if(userRepository.existsByCpf(usuarioCreationDto.getCpf())) {
			throw new DatabaseException("CPF already exists in the database");
		}
		
		if (userRepository.existsByEmail(usuarioCreationDto.getEmail())) {
	        throw new DatabaseException("Email already exists in the database");
	    }
		
		Usuario usuario = new Usuario(usuarioCreationDto);
		
		String senhaCriptografada = passwordEncoder.encode(usuario.getSenha());
		
		usuario.setSenha(senhaCriptografada);
		usuario.setVerificado(false);
		usuario.setStatusExcluido(false);
		
		return userRepository.save(usuario);
	}
	
	@Transactional
	public UsuarioSoftDeleteResponseDto softDelete(Integer usuarioId) {
	    Usuario usuario = userRepository.findById(usuarioId)
	        .orElseThrow(() ->
	            new ResourceNotFoundException(
	                "User with id " + usuarioId + " not found"
	            )
	        );
	    
	    usuario.setStatusExcluido(true);
	    usuario.setDeletedAt(LocalDateTime.now());
	    
	    userRepository.save(usuario);
	    
	    return new UsuarioSoftDeleteResponseDto(usuario);
	}
}
