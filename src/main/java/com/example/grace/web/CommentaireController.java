package com.example.grace.web;

import com.example.grace.entities.Role;
import com.example.grace.entities.User;
import com.example.grace.payload.request.LoginRequest;
import com.example.grace.payload.request.SignupRequest;
import com.example.grace.payload.response.JwtResponse;
import com.example.grace.payload.response.MessageResponse;
import com.example.grace.services.AuthService;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/commentaires")
public class CommentaireController {

    @Autowired
    private CommentaireRepository commentaireRepository;

    @PostMapping("/{postId}")
    public ResponseEntity<Commentaire> addComment(@PathVariable Long postId, @RequestBody Commentaire commentaire, @AuthenticationPrincipal User user) {
        commentaire.setUser(user);
        commentaire.setPost(new Post(postId));
        return ResponseEntity.ok(commentaireRepository.save(commentaire));
    }

    @GetMapping("/post/{postId}")
    public List<Commentaire> getCommentsByPost(@PathVariable Long postId) {
        return commentaireRepository.findByPostId(postId);
    }
}
