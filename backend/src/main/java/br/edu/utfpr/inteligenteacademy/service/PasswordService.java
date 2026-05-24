package br.edu.utfpr.inteligenteacademy.service;

import java.time.Instant;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.utfpr.inteligenteacademy.entity.Usuario;
import br.edu.utfpr.inteligenteacademy.exception.BadRequestException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.model.dto.ChangePasswordRequestDto;
import br.edu.utfpr.inteligenteacademy.repository.UserRepository;

@Service
public class PasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    
	public PasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}
    
	// Lofando trocando senha
	public void changePassword(String email, ChangePasswordRequestDto request ) {
		System.out.println("CHANGE PASSWORD");
		System.out.println(request.getCurrentPassword());
		System.out.println(request.getNewPassword());
		System.out.println(request.getConfirmPassword());
		
	    if (!request.getNewPassword().equals(request.getConfirmPassword())) {
	        throw new BadRequestException("Passwords do not match");
	    }
		
		Usuario usuario = userRepository.findByEmail(email)
		        .orElseThrow(() ->
		        new ResourceNotFoundException(
		                "User with email "
		                + email
		                + " not found"
		        )
	        );
		
	    if (!passwordEncoder.matches(request.getCurrentPassword(), usuario.getPassword())) {
	        throw new BadRequestException("Current password is incorrect.");
	    }
		
	    if (passwordEncoder.matches(request.getNewPassword(), usuario.getPassword())) {
	        throw new BadRequestException("New password must be different from the old password");
	    }
        
        usuario.setSenha(passwordEncoder.encode(request.getNewPassword()));
		usuario.setPasswordChangedAt(Instant.now()); //invalida todos os tokens antigos
		userRepository.save(usuario);
		
	}
	
}
