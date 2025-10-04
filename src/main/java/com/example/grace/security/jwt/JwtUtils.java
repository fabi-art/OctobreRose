package com.example.grace.security.jwt;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.UnsupportedJwtException;


import java.util.Date;

import com.example.grace.services.UserDetailsImpl;

import io.jsonwebtoken.security.Keys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import javax.crypto.SecretKey;

@Component
public class JwtUtils {
	private static final Logger logger = LoggerFactory.getLogger(JwtUtils.class);

	private SecretKey secretKey;

	public JwtUtils() {
		// Générer une clé sécurisée pour HS512
		this.secretKey = Keys.secretKeyFor(SignatureAlgorithm.HS512);
	}

	public String generateJwtToken(Authentication authentication) {
		UserDetailsImpl userPrincipal = (UserDetailsImpl) authentication.getPrincipal();
		// Utilisez secretKey pour signer le JWT
		return Jwts.builder()
				.setSubject(userPrincipal.getUsername())
				.setIssuedAt(new Date())
				.setExpiration(new Date(System.currentTimeMillis() + 86400000)) // 1 jour
				.signWith(secretKey)
				.compact();
	}


	public String getUserNameFromJwtToken(String token) {
		return Jwts.parser()
				.setSigningKey(secretKey)
				.build()
				.parseClaimsJws(token)
				.getBody()
				.getSubject();
	}


	public boolean validateJwtToken(String authToken) {
		try {
			Jwts.parser()
					.setSigningKey(secretKey)
					.build()
					.parseClaimsJws(authToken);
			return true;
		} catch (SignatureException e) {
			logger.error("Invalid JWT signature: {}", e.getMessage());
		} catch (MalformedJwtException e) {
			logger.error("Invalid JWT token: {}", e.getMessage());
		} catch (ExpiredJwtException e) {
			logger.error("JWT token is expired: {}", e.getMessage());
		} catch (UnsupportedJwtException e) {
			logger.error("JWT token is unsupported: {}", e.getMessage());
		} catch (IllegalArgumentException e) {
			logger.error("JWT claims string is empty: {}", e.getMessage());
		}

		return false;
	}
}

