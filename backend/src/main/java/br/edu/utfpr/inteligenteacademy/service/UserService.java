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
import br.edu.utfpr.inteligenteacademy.model.dto.user.UserCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.user.UserResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.user.UserSoftDeleteResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.UserRepository;


@Service
public class UserService {
	private final UserRepository userRepository;
	private final PasswordEncoder passwordEncoder;
	
	public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
	
	@Transactional(readOnly = true)
	public List<UserResponseDto> findAll(){
		List<User> users = userRepository.findAll();
		return users.stream().map(UserResponseDto::new).toList();
	}
	
	
	@Transactional(readOnly = true)
	public User findEntityByEmail(String email) {
		return userRepository.findByEmail(email)
				.orElseThrow(() -> new InvalidCredentialsException("Invalid email or password"));
		
	}

	@Transactional(readOnly = true)
	public User findEntityById(Long userId) {
		return userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException(
						"User with id "
						+ userId
						+ " not found"
				));
	}

	@Transactional(readOnly = true)
	public UserResponseDto findById(Long userId) {
		User user =
		        userRepository.findById(userId)
		        .orElseThrow(() ->
			        new ResourceNotFoundException(
			                "User with id "
			                + userId
			                + " not found"
			        )
		        );
		return new UserResponseDto(user);
	}
	
	@Transactional
	public UserResponseDto save(UserCreationDto userCreationDto) {
		if(userRepository.existsByCpf(userCreationDto.getCpf())) {
			throw new DatabaseException("CPF already exists in the database");
		}
		
		if (userRepository.existsByEmail(userCreationDto.getEmail())) {
	        throw new DatabaseException("Email already exists in the database");
	    }
		
		User user = new User(userCreationDto);
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		user.setVerified(false);
		user.setDeleted(false);
		
		User userSalvo = userRepository.save(user);
		
		return new UserResponseDto(userSalvo);
	}
	
	@Transactional
	public User register(UserCreationDto userCreationDto) {
		if(userRepository.existsByCpf(userCreationDto.getCpf())) {
			throw new DatabaseException("CPF already exists in the database");
		}
		
		if (userRepository.existsByEmail(userCreationDto.getEmail())) {
	        throw new DatabaseException("Email already exists in the database");
	    }
		
		User user = new User(userCreationDto);
		
		String encodedPassword = passwordEncoder.encode(user.getPassword());
		
		user.setPassword(encodedPassword);
		user.setVerified(false);
		user.setDeleted(false);
		
		return userRepository.save(user);
	}
	
	@Transactional
	public UserSoftDeleteResponseDto softDelete(Long userId) {
	    User user = userRepository.findById(userId)
	        .orElseThrow(() ->
	            new ResourceNotFoundException(
	                "User with id " + userId + " not found"
	            )
	        );
	    
	    user.setDeleted(true);
	    user.setDeletedAt(LocalDateTime.now());
	    
	    userRepository.save(user);
	    
	    return new UserSoftDeleteResponseDto(user);
	}
}
