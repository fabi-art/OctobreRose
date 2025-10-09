package com.example.grace.entities;

import jakarta.persistence.*;
import java.util.Date;

@Entity
public class Temoignage {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(columnDefinition = "TEXT")
    private String contenu; // URL du média stocké ici

    @Temporal(TemporalType.TIMESTAMP)
    private Date dateTemoignage;

    @Column(length = 200)
    private String titreTemoignage;

    @Column(columnDefinition = "TEXT")
    private String descriptionTemoignage;

    @Column(length = 50)
    private String type; // IMAGE, VIDEO, AUDIO

    private Boolean statut; // true = validé, false = en attente

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Constructeur par défaut (obligatoire pour JPA)
    public Temoignage() {}

    // Constructeur complet
    public Temoignage(Long id, String contenu, Date dateTemoignage, String titreTemoignage,
                      String descriptionTemoignage, String type, Boolean statut, User user) {
        this.id = id;
        this.contenu = contenu;
        this.dateTemoignage = dateTemoignage;
        this.titreTemoignage = titreTemoignage;
        this.descriptionTemoignage = descriptionTemoignage;
        this.type = type;
        this.statut = statut;
        this.user = user;
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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}