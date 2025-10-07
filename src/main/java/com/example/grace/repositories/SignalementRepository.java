package com.example.grace.repositories;

import com.example.grace.entities.Commentaire;
import com.example.grace.entities.Post;
import com.example.grace.entities.Signalement;
import com.example.grace.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface SignalementRepository extends JpaRepository<Signalement, Long> {
    boolean existsByUserAndPost(User user, Post post);
    boolean existsByUserAndCommentaire(User user, Commentaire commentaire);
    List<Signalement> findByStatut(String statut);

    void deleteByPost(Post post);
    void deleteByCommentaire(Commentaire commentaire);

}
