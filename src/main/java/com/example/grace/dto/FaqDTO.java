package com.example.grace.dto;

public class FAQDTO {
    private Long id;
    private String question;
    private String reponse;
    private String categorie;
    private String description;
    private String titre;
    private Long userId;

    // Constructeurs
    public FAQDTO() {}

    public FAQDTO(Long id, String question, String reponse, String categorie, 
                  String description, String titre, Long userId) {
        this.id = id;
        this.question = question;
        this.reponse = reponse;
        this.categorie = categorie;
        this.description = description;
        this.titre = titre;
        this.userId = userId;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}