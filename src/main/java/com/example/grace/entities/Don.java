package com.example.grace.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Don {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Double montant;
    private String nomDonateur; // facultatif
    private String email; // facultatif
    private String telephoneDon; // facultatif
    private boolean anonyme = false;
    private boolean paiementEffectue = false;
    private Date dateDon = new Date();
    private String paysDon;

    @ManyToOne
    @JoinColumn(name = "campagne_id")
    private Campagne campagne; // 🔥 C’est ce champ que Campagne attend dans mappedBy="campagne"

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = true)
    private User user; // si l’utilisateur est connecté

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Double getMontant() {
        return montant;
    }

    public void setMontant(Double montant) {
        this.montant = montant;
    }

    public String getNomDonateur() {
        return nomDonateur;
    }

    public void setNomDonateur(String nomDonateur) {
        this.nomDonateur = nomDonateur;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephoneDon() {
        return telephoneDon;
    }

    public void setTelephoneDon(String telephoneDon) {
        this.telephoneDon = telephoneDon;
    }

    public boolean isAnonyme() {
        return anonyme;
    }

    public void setAnonyme(boolean anonyme) {
        this.anonyme = anonyme;
    }

    public boolean isPaiementEffectue() {
        return paiementEffectue;
    }

    public void setPaiementEffectue(boolean paiementEffectue) {
        this.paiementEffectue = paiementEffectue;
    }

    public Date getDateDon() {
        return dateDon;
    }

    public void setDateDon(Date dateDon) {
        this.dateDon = dateDon;
    }

    public String getPaysDon() {
        return paysDon;
    }

    public void setPaysDon(String paysDon) {
        this.paysDon = paysDon;
    }

    public Campagne getCampagne() {
        return campagne;
    }

    public void setCampagne(Campagne campagne) {
        this.campagne = campagne;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }




}