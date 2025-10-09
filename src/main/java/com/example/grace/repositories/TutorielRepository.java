package com.example.grace.repositories;

import com.example.grace.entities.Tutoriel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TutorielRepository extends JpaRepository<Tutoriel, Long> {
    // Filtrer par langue
    List<Tutoriel> findByLangue(String langue);
    
    // Rechercher par mot-clé dans le titre
    List<Tutoriel> findByTitreContainingIgnoreCase(String keyword);
    
    // Rechercher par langue et mot-clé
    List<Tutoriel> findByLangueAndTitreContainingIgnoreCase(String langue, String keyword);
}