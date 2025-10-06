package com.example.grace.web;

import com.example.grace.entities.Commentaire;
import com.example.grace.entities.Post;
import com.example.grace.entities.Role;
import com.example.grace.entities.User;
import com.example.grace.payload.request.LoginRequest;
import com.example.grace.payload.request.SignupRequest;
import com.example.grace.payload.response.JwtResponse;
import com.example.grace.payload.response.MessageResponse;
import com.example.grace.repositories.CommentaireRepository;
import com.example.grace.repositories.PostRepository;
import com.example.grace.repositories.UserRepository;
import com.example.grace.services.AuthService;
import com.example.grace.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/commentaires")
public class CommentaireController {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    // Ajouter un commentaire à un post
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/{postId}")
    public ResponseEntity<?> addComment(
            @PathVariable Long postId,
            @RequestBody Commentaire commentaire,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        // Vérifier que le post existe
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post non trouvé"));

        // Vérifier que l'utilisateur existe
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        // Associer le commentaire à l'utilisateur connecté et au post
        commentaire.setUser(user);
        commentaire.setPost(post);

        Commentaire saved = commentaireRepository.save(commentaire);
        return ResponseEntity.ok(saved);
    }

    // Récupérer les commentaires d’un post
    @GetMapping("/post/{postId}")
    public List<Commentaire> getCommentsByPost(@PathVariable Long postId) {
        return commentaireRepository.findByPostId(postId);
    }


    // Modifier un commentaire
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PutMapping("/{commentId}")
    public ResponseEntity<?> updateComment(
            @PathVariable Long commentId,
            @RequestBody Commentaire commentaireDetails,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Commentaire commentaire = commentaireRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));

        // Vérifier que l'utilisateur est l'auteur du commentaire
        if (!commentaire.getUser().getId().equals(userDetails.getId())) {
            return ResponseEntity.status(403).body("Vous ne pouvez pas modifier ce commentaire");
        }

        commentaire.setContenu(commentaireDetails.getContenu());
        commentaireRepository.save(commentaire);

        return ResponseEntity.ok(commentaire);
    }

    // Supprimer un commentaire
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/{commentId}")
    public ResponseEntity<?> deleteComment(
            @PathVariable Long commentId,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Commentaire commentaire = commentaireRepository.findById(commentId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));

        // Vérifier que l'utilisateur est l'auteur du commentaire ou un admin
        if (!commentaire.getUser().getId().equals(userDetails.getId())) {
            return ResponseEntity.status(403).body("Vous ne pouvez pas supprimer ce commentaire");
        }

        commentaireRepository.delete(commentaire);
        return ResponseEntity.noContent().build();
    }

}
