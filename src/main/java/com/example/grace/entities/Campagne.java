package com.example.grace.entities;

import jakarta.persistence.*;
import lombok.Data;
import java.util.Date;
import java.util.List;

@Entity
@Data
public class Campagne {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String description;
    private double montantCible;
    private double montantCollecte = 0.0;
    private Date dateDebut = new Date();
    private Date dateFin;

    private String imageUrl; // optionnelle

    @OneToMany(mappedBy = "campagne", cascade = CascadeType.ALL)
    private List<Don> dons;

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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public double getMontantCible() {
        return montantCible;
    }

    public void setMontantCible(double montantCible) {
        this.montantCible = montantCible;
    }

    public double getMontantCollecte() {
        return montantCollecte;
    }

    public void setMontantCollecte(double montantCollecte) {
        this.montantCollecte = montantCollecte;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDebut) {
        this.dateDebut = dateDebut;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }


    public List<Don> getDons() {
        return dons;
    }

    public void setDons(List<Don> dons) {
        this.dons = dons;
    }


}
