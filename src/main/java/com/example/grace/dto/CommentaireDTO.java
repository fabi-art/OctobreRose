package com.example.grace.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentaireDTO {

    private Long idComment;
    private String contenuComment;
    private String pseudoAuteur; // user.pseudo
    private Date dateComment;
    private Long postId;


    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }


    public Long getIdComment() {
        return idComment;
    }

    public void setIdComment(Long idComment) {
        this.idComment = idComment;
    }

    public String getContenuComment() {
        return contenuComment;
    }

    public void setContenuComment(String contenuComment) {
        this.contenuComment = contenuComment;
    }

    public String getPseudoAuteur() {
        return pseudoAuteur;
    }

    public void setPseudoAuteur(String pseudoAuteur) {
        this.pseudoAuteur = pseudoAuteur;
    }

    public Date getDateComment() {
        return dateComment;
    }

    public void setDateComment(Date dateComment) {
        this.dateComment = dateComment;
    }


}
