package com.example.grace.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Entity
@Table(name = "signalements")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Signalement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // L’utilisateur qui signale
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Le post signalé
    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    // Le commentaire signalé
    @ManyToOne
    @JoinColumn(name = "commentaire_id")
    private Commentaire commentaire;

    // Motif du signalement
    @Column(nullable = false)
    private String motif;

    // Statut du signalement
    @Enumerated(EnumType.STRING)
    private ESignalement statut = ESignalement.EN_ATTENTE;

    // Date du signalement
    private Date dateSignalement = new Date();

    // GETTERS ET SETTERS
    public Date getDateSignalement() {
        return dateSignalement;
    }

    public void setDateSignalement(Date dateSignalement) {
        this.dateSignalement = dateSignalement;
    }

    public String getMotif() {
        return motif;
    }

    public void setMotif(String motif) {
        this.motif = motif;
    }

    public Commentaire getCommentaire() {
        return commentaire;
    }

    public void setCommentaire(Commentaire commentaire) {
        this.commentaire = commentaire;
    }

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }


    public ESignalement getStatut() {
        return statut;
    }

    public void setStatut(ESignalement statut) {
        this.statut = statut;
    }



}
