package com.example.grace.entities;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.Collection;

@Entity
public class FAQ {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, length = 500)
    private String question;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String reponse;

    @Column(length = 100)
    private String categorie;

    @Column(columnDefinition = "TEXT")
    private String description;
    
    @Column(length = 300)
    private String titre;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructeurs
    public FAQ() {}

    public FAQ(Long id, String question, String reponse, String categorie, 
               String description, String titre, User user) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
        this.categorie = categorie;
        this.description = description;
        this.titre = titre;
        this.user = user;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public String getReponse() {
        return reponse;
    }

    public void setReponse(String reponse) {
        this.reponse = reponse;
    }

    public String getCategorie() {
        return categorie;
    }

    public void setCategorie(String categorie) {
        this.categorie = categorie;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}