package br.edu.utfpr.inteligenteacademy.service;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.User;
import br.edu.utfpr.inteligenteacademy.exception.DatabaseException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.exception.auth.InvalidCredentialsException;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioSoftDeleteResponseDto;
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
		List<User> users = usuarioRepository.findAll();
		return users.stream().map(x -> new UsuarioResponseDto(x)).toList();
	}
	
	
	@Transactional(readOnly = true)
	public User findEntityByEmail(String email) {
		return usuarioRepository.findByEmail(email)
				.orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
		
	}
	
	@Transactional(readOnly = true)
	public UsuarioResponseDto findById(Long usuarioId) {
		User user =
		        usuarioRepository.findById(usuarioId)
		        .orElseThrow(() ->
			        new ResourceNotFoundException(
			                "User with id "
			                + usuarioId
			                + " not found"
			        )
		        );
		return new UsuarioResponseDto(user);
	}
	
	@Transactional
	public UsuarioResponseDto save(UsuarioCreationDto usuarioCreationDto) {
		if(usuarioRepository.existsByCpf(usuarioCreationDto.getCpf())) {
			throw new DatabaseException("CPF already exists in the database");
		}
		
		if (usuarioRepository.existsByEmail(usuarioCreationDto.getEmail())) {
	        throw new DatabaseException("Email already exists in the database");
	    }
		
		User user = new User(usuarioCreationDto);
		
		String senhaCriptografada = passwordEncoder.encode(user.getPassword());
		
		user.setPassword(senhaCriptografada);
		user.setVerified(false);
		user.setDeleted(false);
		
		User userSalvo = usuarioRepository.save(user);
		
		return new UsuarioResponseDto(userSalvo);
	}
	
	@Transactional
	public User register(UsuarioCreationDto usuarioCreationDto) {
		if(usuarioRepository.existsByCpf(usuarioCreationDto.getCpf())) {
			throw new DatabaseException("CPF already exists in the database");
		}
		
		if (usuarioRepository.existsByEmail(usuarioCreationDto.getEmail())) {
	        throw new DatabaseException("Email already exists in the database");
	    }
		
		User user = new User(usuarioCreationDto);
		
		String senhaCriptografada = passwordEncoder.encode(user.getPassword());
		
		user.setPassword(senhaCriptografada);
		user.setVerified(false);
		user.setDeleted(false);
		
		return usuarioRepository.save(user);
	}
	
	@Transactional
	public UsuarioSoftDeleteResponseDto softDelete(Long usuarioId) {
	    User user = usuarioRepository.findById(usuarioId)
	        .orElseThrow(() ->
	            new ResourceNotFoundException(
	                "User with id " + usuarioId + " not found"
	            )
	        );
	    
	    user.setDeleted(true);
	    user.setDeletedAt(LocalDateTime.now());
	    
	    usuarioRepository.save(user);
	    
	    return new UsuarioSoftDeleteResponseDto(user);
	}
}
