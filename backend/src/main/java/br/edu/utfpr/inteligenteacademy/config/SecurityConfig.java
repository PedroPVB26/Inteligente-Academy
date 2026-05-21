package br.edu.utfpr.inteligenteacademy.config;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
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
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(localhostOnly()).permitAll()
                .anyRequest().denyAll()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }

    @Bean
    RequestMatcher localhostOnly() {
        return request -> isLocalhostRequest(request);
    }

    @Bean
    PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.addAllowedOriginPattern("http://localhost:*");
        configuration.addAllowedOriginPattern("http://127.0.0.1:*");
        configuration.addAllowedOriginPattern("http://[::1]:*");
        configuration.addAllowedOriginPattern("https://localhost:*");
        configuration.addAllowedOriginPattern("https://127.0.0.1:*");
        configuration.addAllowedOriginPattern("https://[::1]:*");
        configuration.addAllowedMethod("*");
        configuration.addAllowedHeader("*");

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    private boolean isLocalhostRequest(HttpServletRequest request) {
        String serverName = request.getServerName();
        String remoteAddr = request.getRemoteAddr();

        return isLoopbackHost(serverName) || isLoopbackHost(remoteAddr);
    }

    private boolean isLoopbackHost(String value) {
        if (value == null) {
            return false;
        }

        return "localhost".equalsIgnoreCase(value)
                || "127.0.0.1".equals(value)
                || "::1".equals(value);
    }
}