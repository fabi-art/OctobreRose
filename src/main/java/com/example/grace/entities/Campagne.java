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
}
