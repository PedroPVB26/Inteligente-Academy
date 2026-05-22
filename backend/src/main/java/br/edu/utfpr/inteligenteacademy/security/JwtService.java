package br.edu.utfpr.inteligenteacademy.security;

import java.security.Key;
import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.edu.utfpr.inteligenteacademy.entity.Usuario;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenExpiredException;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenInvalidException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.expiration}")
	private Long expiration;


	public String gerarToken(Usuario usuario) {
		return Jwts
				.builder()
				.subject(usuario.getEmail()) // dono do token
				.claim("id", usuario.getId()) // claim -> personalizado para o token
				.claim("role", usuario.getTipoUsuario().name())
				.issuedAt(new Date(System.currentTimeMillis()))
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey()) // assinatura do token
				.compact(); // gera
	}
	
	// Converte string Base64 em uma chave criptográfica real.
	private Key getSignInKey() {
		byte[] keyByte = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyByte);
	}
	
	public String extrairEmail(String token) {
	    try {

	        return Jwts
	                .parser()
	                .verifyWith((SecretKey) getSignInKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload()
	                .getSubject();

	    } catch (ExpiredJwtException e) {

	        throw new TokenExpiredException("JWT token has expired");

	    } catch (JwtException e) {

	        throw new TokenInvalidException("JWT token is invalid");
	    }
	}
	
	public boolean tokenValid(String token) {
		try {
			Jwts
			.parser()
			.verifyWith((SecretKey) getSignInKey())
			.build()
			.parseSignedClaims(token);
			
			return true;
		}catch(Exception e) {
			return false;
		}
	}
}
