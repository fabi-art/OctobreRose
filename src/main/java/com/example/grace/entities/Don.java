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


}