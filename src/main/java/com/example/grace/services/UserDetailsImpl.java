package com.example.grace.services;

import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import com.example.grace.entities.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import com.fasterxml.jackson.annotation.JsonIgnore;

public class UserDetailsImpl implements UserDetails {
	private static final long serialVersionUID = 1L;

	private Long id;

	private String pseudo;

	private String email;


	private String nom;

	private String telephone;

	@JsonIgnore
	private String password;

	private boolean firstLogin;

	private Collection<? extends GrantedAuthority> authorities;

	public UserDetailsImpl(Long id, String pseudo,String nom, String email,String telephone, String password,
						   Collection<? extends GrantedAuthority> authorities, boolean firstLogin) {
		this.id = id;
		this.pseudo = pseudo;
		this.nom = nom;
		this.email = email;
		this.telephone = telephone ;
		this.password = password;
		this.authorities = authorities;
		this.firstLogin = firstLogin;
	}




    public static UserDetailsImpl build(User user) {
		List<GrantedAuthority> authorities = user.getRoles().stream()
				.map(role -> new SimpleGrantedAuthority(role.getName().name()))
				.collect(Collectors.toList());

		return new UserDetailsImpl(
				user.getId(),
				user.getPseudo(),
				user.getNom(),
				user.getEmail(),
				user.getTelephone(),

				user.getPassword(),
				authorities,
				user.isFirstLogin() // Assurez-vous que cette méthode existe dans votre User entity
		);
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return authorities;
	}

	public Long getId() {
		return id;
	}

	public String getEmail() {
		return email;
	}

	public String getNom() {
		return nom;
	}

	public String getTelephone() {
		return telephone;
	}

	@Override
	public String getPassword() {
		return password;
	}

	@Override
	public String getUsername() {
		return pseudo;
	}

	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (o == null || getClass() != o.getClass())
			return false;
		UserDetailsImpl user = (UserDetailsImpl) o;
		return Objects.equals(id, user.id);
	}

	// Ajoutez un getter pour firstLogin
	public boolean isFirstLogin() {
		return firstLogin;
	}
}
