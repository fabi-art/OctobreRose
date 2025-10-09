package com.example.grace.dto;

import com.example.grace.entities.CentreSante;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CentreSanteDTO {
    private Long id;
    private String nom;
    private String adresse;
    private String ville;
    private String pays;
    private String telephone;
    private String coordonneeCentre;
    private String pseudo;

    // Constructeurs, getters et setters
    public CentreSanteDTO(CentreSante centreSante) {
        this.id = centreSante.getId();
        this.nom = centreSante.getNom();
        this.adresse = centreSante.getAdresse();
        this.ville = centreSante.getVille();
        this.pays = centreSante.getPays();
        this.telephone = centreSante.getTelephone();
        this.coordonneeCentre = centreSante.getCoordonneeCentre();
        this.pseudo = centreSante.getUser() != null ? centreSante.getUser().getPseudo() : null;

    }
}

