package com.example.grace.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;


@Entity
@Table(name = "commentaires")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Commentaire {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idComment;

    @NotBlank
    private String contenuComment;

    private Date dateComment = new Date();

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "post_id")
    @JsonManagedReference
    private Post post;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false) // Lien vers l'utilisateur
    @JsonManagedReference
    private User user; // Relation avec User


    public Commentaire(String contenuComment, Date dateComment, User user, Post post) {
        this.contenuComment = contenuComment;
        this.dateComment = dateComment;
        this.user = user;
        this.post = post;
    }

    // Getters et setters
    public Long getIdComment() { return idComment; }
    public void setIdComment(Long idComment) { this.idComment = idComment; }

    public String getContenu() { return contenuComment; }
    public void setContenu(String contenuComment) { this.contenuComment = contenuComment; }

    public Date getDateComment() { return dateComment; }
    public void setDateComment(Date dateComment) { this.dateComment = dateComment; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public Post getPost() { return post; }
    public void setPost(Post post) { this.post = post; }


}