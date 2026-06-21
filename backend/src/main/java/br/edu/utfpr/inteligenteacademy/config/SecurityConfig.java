package br.edu.utfpr.inteligenteacademy.config;

import java.util.List;

import br.edu.utfpr.inteligenteacademy.security.InternalEndpointFilter;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import br.edu.utfpr.inteligenteacademy.exception.CustomAccessDeniedHandler;
import br.edu.utfpr.inteligenteacademy.exception.CustomAuthenticationEntryPoint;
import br.edu.utfpr.inteligenteacademy.model.dto.user.UserRole;
import br.edu.utfpr.inteligenteacademy.security.JwtAuthenticationFilter;

@Configuration
@AllArgsConstructor
public class SecurityConfig {
    private final AsyncConfig asyncConfig;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
	private final InternalEndpointFilter internalEndpointFilter;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;
    private final CustomAuthenticationEntryPoint customAuthenticationEntryPoint;


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

    	String admin = UserRole.ADMIN.name();
    	String educador = UserRole.EDUCADOR.name();
    	String aluno = UserRole.ALUNO.name();
    	
        http
            .cors(Customizer.withDefaults())
            .csrf(csrf -> csrf.disable())
            .sessionManagement(session ->
            	session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
            )
            .exceptionHandling(exception -> exception
                    .accessDeniedHandler(customAccessDeniedHandler)
                    .authenticationEntryPoint(customAuthenticationEntryPoint)
                )
            .authorizeHttpRequests(auth -> auth
                // PARA TESTAR O FRONT
                .anyRequest().permitAll()
            		
//            	// VALIDA QUEM PODE ACESSAR O QUE
                // Públicas
                .requestMatchers("/auth/**").permitAll()

                // Courses
                .requestMatchers(HttpMethod.GET, "/courses/**").permitAll()
                .requestMatchers("/courses/**").hasRole(admin)

                // // Users
                // .requestMatchers("/users/**").hasRole(admin)

                // // Tags
                // .requestMatchers(HttpMethod.GET, "/tags/**").authenticated()
                // .requestMatchers("/tags/**").hasRole(admin)

                // Enrollments
                .requestMatchers(HttpMethod.POST, "/enrollment-requests/**").authenticated()
                .requestMatchers(HttpMethod.GET, "/enrollment-requests/pending").hasAnyRole(educador, admin)
                .requestMatchers(HttpMethod.GET, "/enrollment-requests/**").hasAnyRole(educador, admin)
                .requestMatchers(HttpMethod.PATCH, "/enrollment-requests/**").hasAnyRole(educador, admin)
                .requestMatchers(HttpMethod.PATCH, "/enrollment/**").hasAnyRole(educador, admin)

				// Lessons Update
				.requestMatchers(HttpMethod.PATCH, "/progress/**").authenticated()
				.requestMatchers(HttpMethod.GET, "/progress/**").authenticated()

				.requestMatchers(HttpMethod.GET, "/certificates/*/download").authenticated()
				.requestMatchers(HttpMethod.GET, "/certificates/validate/*").permitAll()

				// Internas
				.requestMatchers("/internal/**").permitAll()

				.requestMatchers(
						"/css/**",
						"/images/**"
				).permitAll()


                // // Swagger
                // .requestMatchers(
                // 	    "/swagger-ui/**",
                // 	    "/swagger-ui.html",
                // 	    "/v3/api-docs/**",
                // 	    "/v3/api-docs",
                // 	    "/swagger-resources/**",
                // 	    "/webjars/**"
                // 	).permitAll()
            )
			.addFilterBefore(internalEndpointFilter, UsernamePasswordAuthenticationFilter.class)
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class);

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
    	corsConfiguration.setAllowedMethods(List.of("GET", "PATCH", "POST", "DELETE", "PUT", "OPTIONS"));
    	corsConfiguration.setAllowedHeaders(List.of("*"));
    
    	UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    	source.registerCorsConfiguration("/**", corsConfiguration);
    	return source;
    }
}