package com.example.grace.entities;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Tutoriel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String titre;
    private String contenu;
    private String urlVideo;
    private String description;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Lien vers l'utilisateur
    private User user; // Relation avec User
}