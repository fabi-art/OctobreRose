package com.example.grace.payload.response;

import java.util.List;

public class JwtResponse {
	private String token;
	private String type = "Bearer";
	private Long id;
	private String pseudo;
	private String email;
	private List<String> roles;
	private boolean isFirstLogin; // Ajoutez ce champ

	public JwtResponse(String accessToken, Long id, String pseudo, String email, List<String> roles, boolean isFirstLogin) {
		this.token = accessToken;
		this.id = id;
		this.pseudo = pseudo;
		this.email = email;
		this.roles = roles;
		this.isFirstLogin = isFirstLogin;
	}

	public String getAccessToken() {
		return token;
	}

	public void setAccessToken(String accessToken) {
		this.token = accessToken;
	}

	public String getTokenType() {
		return type;
	}

	public void setTokenType(String tokenType) {
		this.type = tokenType;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setUsername(String pseudo) {
		this.pseudo = pseudo;
	}

	public List<String> getRoles() {
		return roles;
	}

	public boolean isFirstLogin() {
		return isFirstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		isFirstLogin = firstLogin;
	}


}
