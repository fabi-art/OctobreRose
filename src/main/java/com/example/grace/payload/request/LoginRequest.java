package com.example.grace.payload.request;


import com.example.grace.validation.ValidPassword;
import jakarta.validation.constraints.NotBlank;

public class LoginRequest {
	@NotBlank
	private String pseudo;

	@ValidPassword
	@NotBlank
	private String password;

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo = pseudo;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
}
