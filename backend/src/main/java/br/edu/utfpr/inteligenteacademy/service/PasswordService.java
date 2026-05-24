package br.edu.utfpr.inteligenteacademy.service;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import br.edu.utfpr.inteligenteacademy.entity.PasswordResetToken;
import br.edu.utfpr.inteligenteacademy.entity.Usuario;
import br.edu.utfpr.inteligenteacademy.exception.BadRequestException;
import br.edu.utfpr.inteligenteacademy.exception.ResourceNotFoundException;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenExpiredException;
import br.edu.utfpr.inteligenteacademy.model.dto.ChangePasswordRequestDto;
import br.edu.utfpr.inteligenteacademy.model.dto.ResetPasswordRequestDto;
import br.edu.utfpr.inteligenteacademy.repository.PasswordResetTokenRepository;
import br.edu.utfpr.inteligenteacademy.repository.UserRepository;

@Service
public class PasswordService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final PasswordResetTokenRepository passwordResetTokenRepository;
    private final EmailService emailService;
    
    
    
	public PasswordService(UserRepository userRepository, PasswordEncoder passwordEncoder,
			PasswordResetTokenRepository passwordResetTokenRepository, EmailService emailService) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
		this.passwordResetTokenRepository = passwordResetTokenRepository;
		this.emailService = emailService;
	}

	// --------------------------------- NÃO LOGADO --------------------------------- 
	// Usuário não logado aperta no botão "esqueci minha senha" e passa o email.
	public void requestReset(String email) {
		//  Sempre retorna sem erro, mesmo se o e-mail não existir - não descobrem quais emails estão cadastrados
		userRepository.findByEmail(email).ifPresent(user ->{
			
			// Invalidar tokens anteriores que não foram usados
			passwordResetTokenRepository.deleteByUsuarioAndUsedFalse(user);
			
			String rawToken = UUID.randomUUID().toString();
			
			PasswordResetToken resetToken = new PasswordResetToken();

			resetToken.setUsuario(user);
			resetToken.setToken(passwordEncoder.encode(rawToken));
			resetToken.setExpiresAt(Instant.now().plus(30, ChronoUnit.MINUTES));
			resetToken.setUsed(false);
			
			passwordResetTokenRepository.save(resetToken);
			
			emailService.sendPasswordResetEmail(user.getEmail(), rawToken);
		});
	}
	
	// Front enviou a requisição do usuário contendo a nova senha
	public void resetPassword(ResetPasswordRequestDto resetPasswordRequestDto) {
		Usuario user = userRepository.findByEmail(resetPasswordRequestDto.getEmail())
		        .orElseThrow(() ->
		        new ResourceNotFoundException(
		                "User with email "
		                + resetPasswordRequestDto.getEmail()
		                + " not found"
		        )
	        );
		
		PasswordResetToken resetToken = passwordResetTokenRepository
	            .findByUsuarioAndUsedFalse(user)
	            .orElseThrow(() -> new BadRequestException("Token inválido ou expirado."));
		
		// token bate com o hash no banco?
        if (!passwordEncoder.matches(resetPasswordRequestDto.getToken(), resetToken.getToken())) {
            throw new TokenExpiredException("Token inválido.");
        }
        
        // Atualiza senha
        user.setSenha(passwordEncoder.encode(resetPasswordRequestDto.getNewPassword()));
        user.setPasswordChangedAt(Instant.now()); // invalida JWTs antigos
        userRepository.save(user);
	
        resetToken.setUsed(true);
        passwordResetTokenRepository.save(resetToken);
	}
	
	
	
	// --------------------------------- LOGADO ---------------------------------
	public void changePassword(String email, ChangePasswordRequestDto request ) {		
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
