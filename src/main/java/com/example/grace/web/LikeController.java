package com.example.grace.web;

import com.example.grace.entities.Commentaire;
import com.example.grace.entities.Like;
import com.example.grace.entities.Post;
import com.example.grace.entities.User;
import com.example.grace.repositories.CommentaireRepository;
import com.example.grace.repositories.LikeRepository;
import com.example.grace.repositories.PostRepository;
import com.example.grace.repositories.UserRepository;
import com.example.grace.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/likes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class LikeController {

    @Autowired
    private LikeRepository likeRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentaireRepository commentaireRepository;

    @Autowired
    private UserRepository userRepository;

    // Liker un Post
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/post/{postId}")
    public ResponseEntity<?> likePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post non trouvé"));

        if (likeRepository.existsByUserAndPost(user, post)) {
            return ResponseEntity.badRequest().body("Vous avez déjà liké ce post");
        }

        Like like = new Like();
        like.setUser(user);
        like.setPost(post);
        likeRepository.save(like);

        return ResponseEntity.ok("Post liké avec succès !");
    }

    // Unliker un Post
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/post/{postId}")
    public ResponseEntity<?> unlikePost(@PathVariable Long postId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post non trouvé"));

        Like like = likeRepository.findByUserAndPost(user, post)
                .orElseThrow(() -> new RuntimeException("Like non trouvé"));

        likeRepository.delete(like);
        return ResponseEntity.ok("Like retiré");
    }

    // Liker un Commentaire
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/commentaire/{commentaireId}")
    public ResponseEntity<?> likeComment(@PathVariable Long commentaireId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Commentaire commentaire = commentaireRepository.findById(commentaireId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));

        if (likeRepository.existsByUserAndCommentaire(user, commentaire)) {
            return ResponseEntity.badRequest().body("Vous avez déjà liké ce commentaire 👍");
        }

        Like like = new Like();
        like.setUser(user);
        like.setCommentaire(commentaire);
        likeRepository.save(like);

        return ResponseEntity.ok("Commentaire liké avec succès !");
    }

    // Unliker un Commentaire
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @DeleteMapping("/commentaire/{commentaireId}")
    public ResponseEntity<?> unlikeComment(@PathVariable Long commentaireId, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Commentaire commentaire = commentaireRepository.findById(commentaireId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));

        Like like = likeRepository.findByUserAndCommentaire(user, commentaire)
                .orElseThrow(() -> new RuntimeException("Like non trouvé"));

        likeRepository.delete(like);
        return ResponseEntity.ok("Like retiré");
    }

    // Compter les likes d’un post
    @GetMapping("/post/{postId}/count")
    public ResponseEntity<?> countLikesForPost(@PathVariable Long postId) {
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post non trouvé"));
        long count = likeRepository.countByPost(post);
        return ResponseEntity.ok(count);
    }

    // Compter les likes d’un commentaire
    @GetMapping("/commentaire/{commentaireId}/count")
    public ResponseEntity<?> countLikesForComment(@PathVariable Long commentaireId) {
        Commentaire commentaire = commentaireRepository.findById(commentaireId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));
        long count = likeRepository.countByCommentaire(commentaire);
        return ResponseEntity.ok(count);
    }
}
