package com.example.grace.entities;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "likes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // L'utilisateur qui a liké
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    @JsonManagedReference
    private User user;

    // Le post liké (nullable si c'est un commentaire)
    @ManyToOne
    @JoinColumn(name = "post_id")
    @JsonManagedReference
    private Post post;

    // Le commentaire liké (nullable si c'est un post)
    @ManyToOne
    @JoinColumn(name = "commentaire_id")
    @JsonManagedReference
    private Commentaire commentaire;

    private Date dateLike = new Date();

    // --- Getters / Setters ---
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public Commentaire getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
    }

    public Date getDate() {
        return dateLike;
    }

    public void setDate(Date dateLike) {
        this.dateLike = dateLike;
    }
}

