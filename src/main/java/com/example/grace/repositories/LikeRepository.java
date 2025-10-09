package com.example.grace.repositories;

import com.example.grace.entities.Like;
import com.example.grace.entities.Post;
import com.example.grace.entities.Commentaire;
import com.example.grace.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface LikeRepository extends JpaRepository<Like, Long> {

    // Vérifier si un utilisateur a déjà liké un post
    boolean existsByUserAndPost(User user, Post post);

    // Vérifier si un utilisateur a déjà liké un commentaire
    boolean existsByUserAndCommentaire(User user, Commentaire commentaire);

    // Trouver un like pour suppression
    Optional<Like> findByUserAndPost(User user, Post post);

    Optional<Like> findByUserAndCommentaire(User user, Commentaire commentaire);

    // Compter les likes
    long countByPost(Post post);

    long countByCommentaire(Commentaire commentaire);

    void deleteByPost(Post post);
    void deleteByCommentaire(Commentaire commentaire);


}
