package com.example.grace.repositories;



import com.example.grace.entities.Annonce;
import com.example.grace.entities.CentreSante;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface AnnonceRepository extends JpaRepository<Annonce, Long> {

    // 🔍 Rechercher les annonces par type (ex : dépistage, sensibilisation...)
    List<Annonce> findByTypeAnnonceContainingIgnoreCase(String typeAnnonce);

    // 🔍 Rechercher les annonces liées à un centre de santé précis
    List<Annonce> findByCentreSante(CentreSante centreSante);

    // 🔍 Rechercher les annonces par ville du centre de santé
    List<Annonce> findByCentreSante_VilleIgnoreCase(String ville);
}
