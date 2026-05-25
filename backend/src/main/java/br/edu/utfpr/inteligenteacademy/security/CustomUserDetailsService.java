package br.edu.utfpr.inteligenteacademy.security;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.edu.utfpr.inteligenteacademy.repository.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

    private final UsuarioRepository usuarioRepository;

    public CustomUserDetailsService(
            UsuarioRepository usuarioRepository
    ) {
        this.usuarioRepository = usuarioRepository;
    }
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return usuarioRepository
                .findByEmail(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));
	}

}
