package com.example.grace.repositories;

import com.example.grace.entities.CentreSante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface CentreSanteRepository extends JpaRepository<CentreSante, Long> {

    boolean existsByNomAndVille(String nom, String ville);

    // Recherche exacte
    List<CentreSante> findByVille(String ville);
    List<CentreSante> findByNom(String nom);

    // Recherche partielle (insensible à la casse)
    List<CentreSante> findByVilleContainingIgnoreCaseOrNomContainingIgnoreCase(String ville, String nom);
}