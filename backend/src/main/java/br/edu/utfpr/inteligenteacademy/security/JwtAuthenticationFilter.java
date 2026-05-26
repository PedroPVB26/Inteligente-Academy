package br.edu.utfpr.inteligenteacademy.security;

import java.io.IOException;
import java.time.Instant;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import br.edu.utfpr.inteligenteacademy.entity.User;
import br.edu.utfpr.inteligenteacademy.exception.PasswordChangedException;
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
	    	String email = jwtService.extractEmail(token);


	    	if (
	    			email != null &&
	    			SecurityContextHolder
	    				.getContext()
	    				.getAuthentication() == null
	    	) {

	    		// Busca usuario no banco
	    		UserDetails userDetails = userDetailsService.loadUserByUsername(email);

	    		User user = (User) userDetails;
	    		Instant passwordChangedAt = user.getPasswordChangedAt();

	    		if(passwordChangedAt != null) {
	    			Instant tokenIssuedAt = jwtService.extractIssuedAt(token).toInstant();
	    			if(tokenIssuedAt.isBefore(passwordChangedAt)) {
	    		        throw new PasswordChangedException("Password changed. Please login again.");
	    			}
	    		}
	    		
	    		// Cria autenticação
	    		UsernamePasswordAuthenticationToken authentication =
	    				new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());

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