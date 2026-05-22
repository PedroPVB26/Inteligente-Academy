package br.edu.utfpr.inteligenteacademy.service;

import java.time.LocalDateTime;
import java.util.UUID;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.edu.utfpr.inteligenteacademy.entity.TokenVerificacaoEmail;
import br.edu.utfpr.inteligenteacademy.entity.Usuario;
import br.edu.utfpr.inteligenteacademy.exception.auth.EmailNotVerifiedException;
import br.edu.utfpr.inteligenteacademy.exception.auth.InvalidCredentialsException;
import br.edu.utfpr.inteligenteacademy.exception.auth.UserDeletedException;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenAlreadyUsedException;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenExpiredException;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenInvalidException;
import br.edu.utfpr.inteligenteacademy.model.dto.login.LoginRequestDto;
import br.edu.utfpr.inteligenteacademy.model.dto.login.LoginResponseDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioCreationDto;
import br.edu.utfpr.inteligenteacademy.model.dto.usuario.UsuarioResponseDto;
import br.edu.utfpr.inteligenteacademy.repository.TokenVerificacaoEmailRepository;
import br.edu.utfpr.inteligenteacademy.security.JwtService;

@Service
public class AuthService {
    private final UsuarioService usuarioService;
    private final TokenVerificacaoEmailRepository tokenVerificacaoEmailRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    
    
	public AuthService(
			UsuarioService usuarioService,
			TokenVerificacaoEmailRepository tokenVerificacaoEmailRepository,
			EmailService emailService,
			PasswordEncoder passwordEncoder,
			JwtService jwtService
			) {
		this.usuarioService = usuarioService;
		this.tokenVerificacaoEmailRepository = tokenVerificacaoEmailRepository;
		this.emailService = emailService;
		this.passwordEncoder = passwordEncoder;
		this.jwtService = jwtService;
	}
    
	@Transactional(readOnly = true)
	public LoginResponseDto login(LoginRequestDto loginRequestDto) {
		Usuario usuario = usuarioService.findEntityByEmail(loginRequestDto.getEmail());
		
		boolean senhaCorreta = passwordEncoder.matches(loginRequestDto.getSenha(), usuario.getSenha());
		
		if(!senhaCorreta) {
			throw new InvalidCredentialsException("Invalid email or password");
		}
		
		if(!usuario.getVerificado()) {
			throw new EmailNotVerifiedException("Email not verified");
		}
		
		if (usuario.getStatusExcluido()) {
	        throw new UserDeletedException("User deleted");
	    }

		String token = jwtService.gerarToken(usuario);
		return new LoginResponseDto(token);
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
