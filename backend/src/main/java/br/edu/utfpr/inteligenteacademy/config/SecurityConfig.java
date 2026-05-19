package br.edu.utfpr.inteligenteacademy.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

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
}