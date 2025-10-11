package com.example.grace.entities;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "posts")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Post {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private String contenuPost;

    private Date datePost = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Lien vers l'utilisateur
    @JsonManagedReference
    private User user; // Relation avec User

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Commentaire> commentaire= new ArrayList<>(); // Ajout de la relation

    public Post(String contenuPost, Date datePost, User user) {
        this.contenuPost = contenuPost;
        this.datePost = datePost;
        this.user = user;
    }

    // Getters et setters
    public Long getIdPost() { return id; }
    public void setIdPost(Long idPost) { this.id = id; }

    public String getContenuPost() { return contenuPost; }
    public void setContenuPost(String contenuPost) { this.contenuPost = contenuPost; }

    public Date getDatePost() { return datePost; }
    public void setDatePost(Date datePost) { this.datePost = datePost; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }



}