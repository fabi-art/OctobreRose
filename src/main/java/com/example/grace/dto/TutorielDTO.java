package com.example.grace.dto;

import java.util.Date;

public class TutorielDTO {
    private Long id;
    private String titre;
    private String contenu;
    private String urlVideo; // Chemin du fichier vidéo
    private String description;
    private String langue; // "fr", "moore", "dioula"
    private Date dateCreation;
    private Long userId;

    // Constructeurs
    public TutorielDTO() {}

    public TutorielDTO(Long id, String titre, String contenu, String urlVideo, 
                       String description, String langue, Date dateCreation, Long userId) {
        this.id = id;
        this.titre = titre;
        this.contenu = contenu;
        this.urlVideo = urlVideo;
        this.description = description;
        this.langue = langue;
        this.dateCreation = dateCreation;
        this.userId = userId;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public String getUrlVideo() {
        return urlVideo;
    }

    public void setUrlVideo(String urlVideo) {
        this.urlVideo = urlVideo;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLangue() {
        return langue;
    }

    public void setLangue(String langue) {
        this.langue = langue;
    }

    public Date getDateCreation() {
        return dateCreation;
    }

    public void setDateCreation(Date dateCreation) {
        this.dateCreation = dateCreation;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}