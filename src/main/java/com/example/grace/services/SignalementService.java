package com.example.grace.services;

import com.example.grace.entities.*;
import com.example.grace.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SignalementService {

    @Autowired
    private SignalementRepository signalementRepository;

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentaireRepository commentaireRepository;

    /**
     * Traite un signalement : met à jour le statut, et si ACCEPTE supprime
     * proprement le post ou le commentaire (suppression des likes + signalements liés d'abord).
     */
    @Transactional
    public void traiterSignalement(Long signalementId, ESignalement statut) {
        Signalement s = signalementRepository.findById(signalementId)
                .orElseThrow(() -> new RuntimeException("Signalement non trouvé"));

        s.setStatut(statut);
        signalementRepository.save(s);

        if (statut == ESignalement.ACCEPTE) {
            if (s.getPost() != null) {
                Post post = s.getPost();

                // 1) supprimer les likes attachés au post
                likeRepository.deleteByPost(post);

                // 2) supprimer tous les signalements liés à ce post (y compris celui courant si présent)
                signalementRepository.deleteByPost(post);

                // 3) supprimer le post lui-même
                postRepository.delete(post);
            } else if (s.getCommentaire() != null) {
                Commentaire commentaire = s.getCommentaire();

                // 1) supprimer les likes attachés au commentaire
                likeRepository.deleteByCommentaire(commentaire);

                // 2) supprimer tous les signalements liés à ce commentaire
                signalementRepository.deleteByCommentaire(commentaire);

                // 3) supprimer le commentaire
                commentaireRepository.delete(commentaire);
            }
        }
        // Si EN_ATTENTE ou REJETE -> on ne supprime rien (déjà sauvegardé le statut)
    }
}
