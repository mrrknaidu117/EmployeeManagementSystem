package com.example.jpa.util;

import java.util.Date;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javax.crypto.SecretKey;


@Component
public class JwtTokenProvider {
	private static final Logger logger = LoggerFactory.getLogger(JwtTokenProvider.class);
	@Value("${app.jwt-secret}")
	private String jwtSecret;
	@Value("${app.jwt-expiration-ms}")
	private int jwtExpirationInMs;
	
	public String generateToken(String username) {
		Date now = new Date();
		Date expirationDate = new Date(now.getTime()+jwtExpirationInMs);
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		
		return Jwts.builder()
				.setSubject(username)
				.setIssuedAt(new Date())
				.setExpiration(expirationDate)
				.signWith(key, SignatureAlgorithm.HS512)
				.compact();
	}
	
	public String getUsernameFromJWT(String token) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		Claims claims = Jwts.parserBuilder()
				.setSigningKey(key)
				.build()
				.parseClaimsJws(token)
				.getBody();
		return claims.getSubject();
	}
	
	public boolean validateToken(String authToken) {
		SecretKey key = Keys.hmacShaKeyFor(jwtSecret.getBytes());
		try {
			Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(authToken);
			return true;
		}catch(JwtException | IllegalArgumentException e) {
			logger.error("JWT validation error: {}",e.getMessage());
		}
		return false;
	}
}
