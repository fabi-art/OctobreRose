package com.example.grace.dto;

import java.util.Date;

public class TemoignageDTO {
    private Long id;
    private String contenu;
    private Date dateTemoignage;
    private String titreTemoignage;
    private String descriptionTemoignage;
    private String type; // IMAGE, VIDEO, AUDIO
    private Boolean statut;
    private String mediaUrl; // URL/chemin du média
    private Long userId;

    // Constructeurs
    public TemoignageDTO() {}

    public TemoignageDTO(Long id, String contenu, Date dateTemoignage, String titreTemoignage, 
                         String descriptionTemoignage, String type, Boolean statut, 
                         String mediaUrl, Long userId) {
        this.id = id;
        this.contenu = contenu;
        this.dateTemoignage = dateTemoignage;
        this.titreTemoignage = titreTemoignage;
        this.descriptionTemoignage = descriptionTemoignage;
        this.type = type;
        this.statut = statut;
        this.mediaUrl = mediaUrl;
        this.userId = userId;
    }

    // Getters et Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenu() {
        return contenu;
    }

    public void setContenu(String contenu) {
        this.contenu = contenu;
    }

    public Date getDateTemoignage() {
        return dateTemoignage;
    }

    public void setDateTemoignage(Date dateTemoignage) {
        this.dateTemoignage = dateTemoignage;
    }

    public String getTitreTemoignage() {
        return titreTemoignage;
    }

    public void setTitreTemoignage(String titreTemoignage) {
        this.titreTemoignage = titreTemoignage;
    }

    public String getDescriptionTemoignage() {
        return descriptionTemoignage;
    }

    public void setDescriptionTemoignage(String descriptionTemoignage) {
        this.descriptionTemoignage = descriptionTemoignage;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getStatut() {
        return statut;
    }

    public void setStatut(Boolean statut) {
        this.statut = statut;
    }

    public String getMediaUrl() {
        return mediaUrl;
    }

    public void setMediaUrl(String mediaUrl) {
        this.mediaUrl = mediaUrl;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}

