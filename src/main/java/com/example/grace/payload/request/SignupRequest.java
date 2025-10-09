package com.example.grace.payload.request;

import com.example.grace.validation.ValidPassword;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.util.List;
import java.util.Set;



public class SignupRequest {
    @NotBlank
    @Size(min = 3, max = 20)
    private String pseudo;
    @NotBlank
    @Size(min = 2, max = 50)
    private String nom;


    @NotBlank
    @Size(min = 2, max = 50)
    private String ville;

    @NotBlank
    @Size(min = 6, max = 20)
    private String telephone;

    @NotBlank
    @Size(max = 50)
    @Email
    private String email;

    private Set<String> role;


    @ValidPassword
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;

    public @NotBlank @Size(min = 6, max = 40) String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(@NotBlank @Size(min = 6, max = 40) String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    @NotBlank
    @Size(min = 6, max = 40)
    private String confirmPassword;


    public String getPseudo() {
        return pseudo;
    }

    public void setPseudo(String pseudo) {
        this.pseudo = pseudo;
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

    public Set<String> getRole() {
      return this.role;
    }

    public void setRole(Set<String> role) {
      this.role = role;
    }

    // Ajoute les getters et setters
    public String getNom() { return nom; }
    public void setNom(String nom) { this.nom = nom; }

    public String getVille() { return ville; }
    public void setVille(String ville) { this.ville = ville; }

    public String getTelephone() { return telephone; }
    public void setTelephone(String telephone) { this.telephone = telephone; }

}
