package com.example.grace.web;

import com.example.grace.entities.*;
import com.example.grace.repositories.*;
import com.example.grace.services.SignalementService;
import com.example.grace.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/signalements")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SignalementController {

    @Autowired
    private SignalementRepository signalementRepository;

    @Autowired
    private SignalementService signalementService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private CommentaireRepository commentaireRepository;

    // Un USER signale un post
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/post/{postId}")
    public ResponseEntity<?> signalerPost(
            @PathVariable Long postId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody String motif
    ) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Post post = postRepository.findById(postId)
                .orElseThrow(() -> new RuntimeException("Post non trouvé"));

        if (signalementRepository.existsByUserAndPost(user, post)) {
            return ResponseEntity.badRequest().body(" Vous avez déjà signalé ce post.");
        }

        Signalement signalement = new Signalement();
        signalement.setUser(user);
        signalement.setPost(post);
        signalement.setMotif(motif);
        signalementRepository.save(signalement);

        return ResponseEntity.ok(" Post signalé avec succès !");
    }

    //  Un USER signale un commentaire
    @PreAuthorize("hasRole('USER') or hasRole('ADMIN')")
    @PostMapping("/commentaire/{commentaireId}")
    public ResponseEntity<?> signalerCommentaire(
            @PathVariable Long commentaireId,
            @AuthenticationPrincipal UserDetailsImpl userDetails,
            @RequestBody String motif
    ) {
        User user = userRepository.findById(userDetails.getId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        Commentaire commentaire = commentaireRepository.findById(commentaireId)
                .orElseThrow(() -> new RuntimeException("Commentaire non trouvé"));

        if (signalementRepository.existsByUserAndCommentaire(user, commentaire)) {
            return ResponseEntity.badRequest().body(" Vous avez déjà signalé ce commentaire.");
        }

        Signalement signalement = new Signalement();
        signalement.setUser(user);
        signalement.setCommentaire(commentaire);
        signalement.setMotif(motif);
        signalementRepository.save(signalement);

        return ResponseEntity.ok(" Commentaire signalé avec succès !");
    }

    //  ADMIN : voir tous les signalements
    @PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public ResponseEntity<List<Signalement>> getAllSignalements() {
        return ResponseEntity.ok(signalementRepository.findAll());
    }

    // ADMIN : traiter un signalement
    @PreAuthorize("hasRole('ADMIN')")
    @PutMapping("/{id}/traiter")
    public ResponseEntity<?> traiterSignalement(
            @PathVariable Long id,
            @RequestParam("statut") ESignalement statut
    ) {
        signalementService.traiterSignalement(id, statut);
        return ResponseEntity.ok("Signalement traité, statut : " + statut);
    }


}
