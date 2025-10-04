package com.example.grace.entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int idPost;

    private String contenu;

    private Date datePost;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Lien vers l'utilisateur
    private User user; // Relation avec User

    @OneToMany(mappedBy = "post", fetch = FetchType.LAZY)
    private Collection<Commentaire> commentaire= new ArrayList<>(); // Ajout de la relation

}