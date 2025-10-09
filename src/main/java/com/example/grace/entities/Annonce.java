package com.example.grace.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Annonce {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idAnnonce;

    private String titreAnnonce;
    // Si tu veux garder les autres champs commentés de dev-anita, tu peux les activer plus tard
    // private String lieuAnnonce;
    // private String coordonneesAnnonce;

    private String typeAnnonce;
    private String descAnnonce;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Lien vers l'utilisateur
    private User user; // Relation avec User

    @ManyToOne
    @JoinColumn(name = "centreSante_id")
    private CentreSante centreSante;
}
