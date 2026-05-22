package br.edu.utfpr.inteligenteacademy.security;

import java.io.IOException;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

	private final CustomUserDetailsService userDetailsService;
	private final HandlerExceptionResolver handlerExceptionResolver;
	private final JwtService jwtService;
	
	public JwtAuthenticationFilter(
	        JwtService jwtService,
	        CustomUserDetailsService userDetailsService,
	        HandlerExceptionResolver handlerExceptionResolver) {
	    this.jwtService = jwtService;
	    this.userDetailsService = userDetailsService;
	    this.handlerExceptionResolver = handlerExceptionResolver;
	}
	
	@Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

    	try {

	    	String authorizationHeader = request.getHeader("Authorization");

	    	if (
	    			authorizationHeader == null ||
	    			!authorizationHeader.startsWith("Bearer ")
	    	) {
	    		filterChain.doFilter(request, response);
	    		return;
	    	}

	    	String token = authorizationHeader.substring(7);

	    	// Extrai email e valida automaticamente:
	    	// - assinatura
	    	// - expiração
	    	// - formato do token
	    	String email = jwtService.extrairEmail(token);

	    	// Evita autenticar duas vezes
	    	if (
	    			email != null &&
	    			SecurityContextHolder
	    				.getContext()
	    				.getAuthentication() == null
	    	) {

	    		// Busca usuario no banco
	    		UserDetails userDetails =
	    				userDetailsService.loadUserByUsername(email);

	    		// Cria autenticação
	    		UsernamePasswordAuthenticationToken authentication =
	    				new UsernamePasswordAuthenticationToken(
	    						userDetails,
	    						null,
	    						userDetails.getAuthorities()
	    				);

	    		// Adiciona detalhes da request
	    		authentication.setDetails(
	    				new WebAuthenticationDetailsSource()
	    						.buildDetails(request)
	    		);

	    		// Autentica usuário no Spring Security
	    		SecurityContextHolder
	    				.getContext()
	    				.setAuthentication(authentication);
	    	}

	    	filterChain.doFilter(request, response);

    	} catch (Exception e) {

    		// Delega tratamento para o GlobalExceptionHandler
    		handlerExceptionResolver.resolveException(
    				request,
    				response,
    				null,
    				e
    		);
    	}
    }
    
    
}