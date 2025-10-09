package com.example.grace.repositories;

import com.example.grace.entities.FAQ;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

// import org.springframework.data.domain.Page;
// import org.springframework.data.domain.Pageable;

@Repository
public interface FAQRepository extends JpaRepository<FAQ, Long> {
    // Rechercher par catégorie
    List<FAQ> findByCategorie(String categorie);
    
    // Rechercher par mot-clé dans la question
    List<FAQ> findByQuestionContainingIgnoreCase(String keyword);

    
    // Page<FAQ> findByCategorieIgnoreCase(String categorie, Pageable pageable);

    // Page<FAQ> findByQuestionContainingIgnoreCaseOrReponseContainingIgnoreCase(
    //         String q1, String q2, Pageable pageable
    // );

    // Page<FAQ> findByUser_Id(Long userId, Pageable pageable);
}
