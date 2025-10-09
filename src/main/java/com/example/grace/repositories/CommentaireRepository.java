package com.example.grace.repositories;


import com.example.grace.entities.Commentaire;
import com.example.grace.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentaireRepository extends JpaRepository<Commentaire, Long> {
    List<Commentaire> findByPostId(Long postId);

}
