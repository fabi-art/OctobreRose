package com.example.grace.services;

import com.example.grace.dto.CommentaireDTO;
import com.example.grace.entities.Commentaire;
import com.example.grace.entities.Post;
import com.example.grace.entities.User;
import com.example.grace.repositories.CommentaireRepository;
import com.example.grace.repositories.PostRepository;
import com.example.grace.repositories.UserRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CommentaireService {

    private final CommentaireRepository commentaireRepository;
    private final PostRepository postRepository;
    private final UserRepository userRepository;

    public CommentaireService(CommentaireRepository commentaireRepository,
                              PostRepository postRepository,
                              UserRepository userRepository) {
        this.commentaireRepository = commentaireRepository;
        this.postRepository = postRepository;
        this.userRepository = userRepository;
    }

    public CommentaireDTO addComment(Long postId, String contenuComment, Long userId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post non trouvé"));
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        Commentaire commentaire = new Commentaire();
        commentaire.setContenu(contenuComment);
        commentaire.setPost(post);
        commentaire.setUser(user);

        Commentaire saved = commentaireRepository.save(commentaire);

        return mapToDTO(saved);
    }

    public List<CommentaireDTO> getCommentsByPost(Long postId) {
        return commentaireRepository.findByPostId(postId)
                .stream()
                .map(this::mapToDTO)
                .collect(Collectors.toList());
    }

    private CommentaireDTO mapToDTO(Commentaire c) {
        return new CommentaireDTO(
        );
    }

    // Méthode pour sauvegarder un commentaire
    public CommentaireDTO saveComment(Commentaire commentaire) {
        Commentaire saved = commentaireRepository.save(commentaire);

        // Transformer l'entité en DTO pour le frontend
        CommentaireDTO dto = new CommentaireDTO();
        dto.setIdComment(saved.getIdComment());
        dto.setContenuComment(saved.getContenu());
        dto.setPseudoAuteur(saved.getUser().getPseudo());
        dto.setPostId(saved.getPost().getId());
        dto.setDateComment(saved.getDateComment());

        return dto;
    }
}
