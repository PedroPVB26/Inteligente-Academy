package br.edu.utfpr.inteligenteacademy.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.TokenVerificacaoEmail;
import br.edu.utfpr.inteligenteacademy.entity.Usuario;
import br.edu.utfpr.inteligenteacademy.exception.TokenAlreadyUsedException;
import br.edu.utfpr.inteligenteacademy.exception.TokenExpiredException;
import br.edu.utfpr.inteligenteacademy.exception.TokenInvalidException;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.TokenVerificacaoEmailRepository;

@Service
public class AuthService {
    private final UsuarioService usuarioService;
    private final TokenVerificacaoEmailRepository tokenVerificacaoEmailRepository;
    private final EmailService emailService;
    
    
	public AuthService(UsuarioService usuarioService, TokenVerificacaoEmailRepository tokenVerificacaoEmailRepository, EmailService emailService) {
		this.usuarioService = usuarioService;
		this.tokenVerificacaoEmailRepository = tokenVerificacaoEmailRepository;
		this.emailService = emailService;
	}
    
    @Transactional
    public UsuarioResponseDto register(UsuarioCreationDto usuarioCreationDto) {
    	Usuario usuarioSalvo = usuarioService.register(usuarioCreationDto);
    	
    	// Token
    	String token = UUID.randomUUID().toString();
    	TokenVerificacaoEmail tokenEntity = new TokenVerificacaoEmail();
    	tokenEntity.setToken(token);
    	tokenEntity.setUsuario(usuarioSalvo);
    	tokenEntity.setExpiracao(LocalDateTime.now().plusHours(1));
    	tokenVerificacaoEmailRepository.save(tokenEntity);
    	
    	// Email
    	String link = "http://localhost:8081/auth/verificar-email?token=" + token;
    	
        emailService.enviarEmail(
        		usuarioSalvo.getEmail(),
                "Verifique seu email",
                link
        );
        
        return new UsuarioResponseDto(usuarioSalvo);
    }
    
    @Transactional
    public void verifyEmail(String token) {
    	// Busca token
    	TokenVerificacaoEmail tokenEntity = tokenVerificacaoEmailRepository
    			.findByToken(token)
    			.orElseThrow(() -> new TokenInvalidException("Token does not exists"));
    	
    	// Verifica se já foi utilizado
    	Usuario usuario = tokenEntity.getUsuario();

    	if(tokenEntity.isUsado() || usuario.getVerificado()) {
    		throw new TokenAlreadyUsedException("Token already used");
    	}
    	
    	if(tokenEntity.getExpiracao().isBefore(LocalDateTime.now())) {
    		throw new TokenExpiredException("Token expired");
    	}

    	usuario.setVerificado(true);
    	tokenEntity.setUsado(true);
    
    	tokenVerificacaoEmailRepository.save(tokenEntity);
    }
}
