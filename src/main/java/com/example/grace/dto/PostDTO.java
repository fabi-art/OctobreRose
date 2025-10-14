package com.example.grace.dto;

import com.example.grace.entities.Post;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class PostDTO {
    private Long id;
    private String contenuPost;
    private Date datePost;
    private String userPseudo; // On expose seulement le pseudo de l’utilisateur
    private int nbCommentaires;
    private int nbLikes;

    // Constructeur
    public PostDTO(Post post) {
        this.id = post.getId();
        this.contenuPost = post.getContenuPost();
        this.datePost = post.getDatePost();
        this.userPseudo = post.getUser().getPseudo();
        this.nbCommentaires = post.getCommentaire().size();
        this.nbLikes = post.getLike() != null ? post.getLike().size() : 0; // si tu as une relation Like
    }

    public int getNbCommentaires() {
        return nbCommentaires;
    }

    public void setNbCommentaires(int nbCommentaires) {
        this.nbCommentaires = nbCommentaires;
    }

    public int getNbLikes() {
        return nbLikes;
    }

    public void setNbLikes(int nbLikes) {
        this.nbLikes = nbLikes;
    }

    ;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContenuPost() {
        return contenuPost;
    }

    public void setContenuPost(String contenuPost) {
        this.contenuPost = contenuPost;
    }

    public Date getDatePost() {
        return datePost;
    }

    public void setDatePost(Date datePost) {
        this.datePost = datePost;
    }

    public String getUserPseudo() {
        return userPseudo;
    }

    public void setUserPseudo(String userPseudo) {
        this.userPseudo = userPseudo;
    }


}
