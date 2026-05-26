package br.edu.utfpr.inteligenteacademy.security;

import java.util.Date;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import br.edu.utfpr.inteligenteacademy.entity.User;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenExpiredException;
import br.edu.utfpr.inteligenteacademy.exception.token.TokenInvalidException;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Service
public class JwtService {

	@Value("${jwt.secret}")
	private String secretKey;

	@Value("${jwt.access-token-expiration}")
	private Long expiration;


	public String generateAccessToken(User user) {
		return Jwts
				.builder()
				.subject(user.getEmail()) // dono do token
				.claim("id", user.getId()) // claim -> personalizado para o token
				.claim("role", user.getRole().name())
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(getSignInKey()) // assinatura do token
				.compact(); // gera
	}
	
	// Converte string Base64 em uma chave criptográfica real.
	private SecretKey getSignInKey() {
		byte[] keyByte = Decoders.BASE64.decode(secretKey);
		return Keys.hmacShaKeyFor(keyByte);
	}
	
    public Date extractIssuedAt(String token) {
        return extractClaims(token).getIssuedAt();
    }

    public String extractUserId(String token) {
        return extractClaims(token).getSubject();
    }
	
    private Claims extractClaims(String token) {
        return Jwts.parser()
            .verifyWith(getSignInKey())
            .build()
            .parseSignedClaims(token)
            .getPayload();
    }
    
	public String extractEmail(String token) {
	    try {

	        return Jwts
	                .parser()
	                .verifyWith((SecretKey) getSignInKey())
	                .build()
	                .parseSignedClaims(token)
	                .getPayload()
	                .getSubject();

	    } catch (ExpiredJwtException e) {
	        throw new TokenExpiredException("Access token has expired");
	    } catch (JwtException e) {
	        throw new TokenInvalidException("Access token is invalid");
	    }
	}
	
	public boolean isTokenValid(String token) {
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
