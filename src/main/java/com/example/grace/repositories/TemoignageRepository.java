package com.example.grace.repositories;

import com.example.grace.entities.Temoignage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TemoignageRepository extends JpaRepository<Temoignage, Long> {
    // Trouver les témoignages par statut (validés ou non)
    List<Temoignage> findByStatut(Boolean statut);
    
    // Trouver les témoignages par type de média
    List<Temoignage> findByType(String type);
    
    // Trouver les témoignages validés uniquement
    List<Temoignage> findByStatutTrue();
}