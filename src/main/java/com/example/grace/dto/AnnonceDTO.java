package com.example.grace.dto;


import com.example.grace.entities.Annonce;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AnnonceDTO {

    private int idAnnonce;
    private String titreAnnonce;
    private String typeAnnonce;
    private String descAnnonce;

    private Long centreSanteId;     // Pour stocker l’ID du centre lié
    private String centreSanteNom;// Pour afficher le nom du centre
    private String centreSanteCoordonnee;
    private String username;        // Pour afficher le nom de l’utilisateur

    // ✅ Constructeur qui convertit directement depuis une entité Annonce
    public AnnonceDTO(Annonce annonce) {
        this.idAnnonce = annonce.getIdAnnonce();
        this.titreAnnonce = annonce.getTitreAnnonce();
        this.typeAnnonce = annonce.getTypeAnnonce();
        this.descAnnonce = annonce.getDescAnnonce();

        // Gestion des relations (éviter les NullPointerException)
        if (annonce.getCentreSante() != null) {
            this.centreSanteId = annonce.getCentreSante().getId();
            this.centreSanteNom = annonce.getCentreSante().getNom();
            this.centreSanteCoordonnee = annonce.getCentreSante().getCoordonneeCentre();
        }

        if (annonce.getUser() != null) {
            this.username = annonce.getUser().getPseudo(); // ou getUsername() selon ton entité User
        }
    }
}

