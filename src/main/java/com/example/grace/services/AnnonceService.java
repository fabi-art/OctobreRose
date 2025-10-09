package com.example.grace.services;


import com.example.grace.entities.Annonce;

import java.util.List;

public interface AnnonceService {

    // Créer une nouvelle annonce
    void createAnnonce(Annonce annonce);

    // Récupérer toutes les annonces
    List<Annonce> getAnnonces();

    // Récupérer une annonce par ID
    Annonce getAnnonceById(long id);

    // Supprimer une annonce
    void deleteAnnonce(long id);

    // Mettre à jour une annonce
    Annonce updateAnnonce(Annonce annonce, long id);

    // Rechercher les annonces par type
    List<Annonce> searchByType(String typeAnnonce);

    // Récupérer les annonces pour une ville donnée
    List<Annonce> getAnnoncesByVille(String ville);
}
