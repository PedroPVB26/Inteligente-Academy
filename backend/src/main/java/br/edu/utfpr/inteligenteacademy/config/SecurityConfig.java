package br.edu.utfpr.inteligenteacademy.config;

import java.util.List;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
public class SecurityConfig {

	/*
	 * Configuração temporária utilizada durante a fase inicial
	 * de desenvolvimento da API.
	 *
	 * Atualmente todas as rotas estão liberadas para facilitar
	 * testes via Postman e integração inicial do backend.
	 *
	 * Futuramente esta configuração será substituída por um
	 * mecanismo completo de autenticação e autorização utilizando
	 * Spring Security e JWT, protegendo os endpoints da aplicação
	 * conforme o perfil e permissões do usuário.
	 */
    @Bean
    SecurityFilterChain securityFilterChain(
            HttpSecurity http
    ) throws Exception {

        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
    	CorsConfiguration corsConfiguration = new CorsConfiguration();
    	
    	corsConfiguration.setAllowedOrigins(List.of("http://localhost:5173"));
    	corsConfiguration.setAllowedMethods(List.of("GET", "POST", "DELETE", "PUT", "OPTIONS"));
    	corsConfiguration.setAllowedHeaders(List.of("*"));
    
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", corsConfiguration);
    	return source;
    }
}