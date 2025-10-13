package com.example.grace.web;

import com.example.grace.dto.PostMapper;
import com.example.grace.entities.Post;
import com.example.grace.entities.Role;
import com.example.grace.entities.User;
import com.example.grace.payload.request.LoginRequest;
import com.example.grace.payload.request.SignupRequest;
import com.example.grace.payload.response.JwtResponse;
import com.example.grace.payload.response.MessageResponse;
import com.example.grace.repositories.PostRepository;
import com.example.grace.repositories.UserRepository;
import com.example.grace.services.AuthService;
import com.example.grace.services.UserDetailsImpl;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/posts")
public class PostController {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PostMapper postMapper;

    // Creer un post
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PostMapping
    public ResponseEntity<Post> createPost(@RequestBody Post post, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        User user = userRepository.findById(userDetails.getId()).orElseThrow();
        post.setUser(user);
        return ResponseEntity.ok(postRepository.save(post));
    }


    //Liste des post
    @GetMapping
    public List<Post> getAllPosts() {
        return postRepository.findAll();
    }

    // Recuperer un post
    @GetMapping("/{id}")
    public ResponseEntity<Post> getPostById(@PathVariable("id") Long idPost) {
        return postRepository.findById(idPost)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Modifier un post
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    public ResponseEntity<Post> updatePost(
            @PathVariable("id") Long idPost,
            @RequestBody Post postRequest,
            @AuthenticationPrincipal UserDetailsImpl userDetails) {

        Post post = postRepository.findById(idPost)
                .orElseThrow(() -> new RuntimeException("Post non trouvé"));

        // Vérifier que l'utilisateur est bien le propriétaire du post
        if (!post.getUser().getId().equals(userDetails.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }

        // Mettre à jour le contenu du post
        post.setContenuPost(postRequest.getContenuPost());
        Post updatedPost = postRepository.save(post);
        return ResponseEntity.ok(updatedPost);
    }


    //Supprimer un post
    @PreAuthorize("hasRole('ROLE_USER') or hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deletePost(@PathVariable("id") Long idPost, @AuthenticationPrincipal UserDetailsImpl userDetails) {
        Post post = postRepository.findById(idPost).orElseThrow();
        if (!post.getUser().getId().equals(userDetails.getId())) {
            return ResponseEntity.status(HttpStatus.FORBIDDEN).build();
        }
        postRepository.delete(post);
        return ResponseEntity.noContent().build();
    }
}
