package com.example.grace.entities;


import com.example.grace.validation.ValidPassword;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.*;

@Entity
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
@Table(name = "users",
		uniqueConstraints = {
				@UniqueConstraint(columnNames = "pseudo"),
				@UniqueConstraint(columnNames = "email")
		})
public class User {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@NotBlank(message = "Pseudo is mandatory")
	@Size(max = 20, message = "Pseudo must be less than or equal to 20 characters")
	@Column(length = 20, unique = true, nullable = false)
	private String pseudo;

	@NotBlank(message = "Telephone is mandatory")
	@Size(max = 20, message = "Pseudo must be less than or equal to 20 characters")
	@Column(length = 20, unique = true, nullable = false)
	private String telephone;

	@NotBlank(message = "Nom is mandatory")
	@Size(max = 20, message = "Nom must be less than or equal to 20 characters")
	@Column(length = 20, unique = true, nullable = false)
	private String nom;



	@ValidPassword
	@NotBlank(message = "Password is mandatory")
	@Size(max = 120, message = "Password must be less than or equal to 120 characters")
	@Column(length = 120, nullable = false)
	private String password;

	@NotBlank(message = "Email is mandatory")
	@Size(max = 50, message = "Email must be less than or equal to 50 characters")
	@Email(message = "Email should be valid")
	@Column(length = 50, unique = true, nullable = false)
	private String email;


	@Transient  // This annotation indicates that the field is not stored in the database
	@NotBlank(message = "Confirm Password is mandatory")
	@Size(max = 120, message = "Confirm Password must be less than or equal to 120 characters")
	private String confirmPassword;


	@Column(nullable = false)
	private boolean firstLogin = true; // Default to true when a user is created


	private boolean active = true; // Par défaut, un utilisateur est actif

    public <T> User(long l, String testUser, String mail, String encodedPassword, boolean b, List<T> ts) {
    }

	public User(String testUser) {
	}

	// Getters et Setters
	public boolean isActive() {
		return active;
	}

	public void setActive(boolean active) {
		this.active = active;
	}



	@ManyToMany(fetch = FetchType.LAZY)
	@JoinTable(	name = "user_roles",
				joinColumns = @JoinColumn(name = "user_id"),
				inverseJoinColumns = @JoinColumn(name = "role_id"))
	private Set<Role> roles = new HashSet<>();

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Annonce> annonce = new ArrayList<>(); // Ajout de la relation

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private Collection<Don> don = new ArrayList<>(); // Ajout de la relation

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<FAQ> faq = new ArrayList<>(); // Ajout de la relation

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Post> post = new ArrayList<>(); // Ajout de la relation

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Temoignage> temoignage = new ArrayList<>(); // Ajout de la relation

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Tutoriel> tutoriel = new ArrayList<>(); // Ajout de la relation

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Evenement> evenement = new ArrayList<>(); // Ajout de la relation

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<MiniJeuEducatif> mininJeuEducatif= new ArrayList<>(); // Ajout de la relation

	@OneToMany(mappedBy = "user", fetch = FetchType.LAZY)
	private List<Commentaire> commentaire= new ArrayList<>(); // Ajout de la relation


	public User() {
	}

	public User(String pseudo, String nom,String email,String telephone, String password) {
		this.pseudo = pseudo;
		this.nom = nom;
		this.email= email;
		this.telephone=telephone;
		this.password = password;
	}


	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getPseudo() {
		return pseudo;
	}

	public void setPseudo(String pseudo) {
		this.pseudo= pseudo;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}


	public boolean isFirstLogin() {
		return firstLogin;
	}

	public void setFirstLogin(boolean firstLogin) {
		this.firstLogin = firstLogin;
	}





}
