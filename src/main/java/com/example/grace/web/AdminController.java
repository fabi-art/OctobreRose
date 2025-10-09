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
@RequestMapping("/api/admin")
public class AdminController {


    @Autowired
    AuthService authService;

    private static final Logger logger = LoggerFactory.getLogger(AuthController.class);



    // Endpoint pour lister tous les utilisateurs
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users")
    public ResponseEntity<List<User>> listUsers() {
        List<User> users = authService.listUsers();
        return ResponseEntity.ok(users);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/pages")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "10") int size) {
        return authService.listUserPages(page, size);
    }


    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/users/r")
    public ResponseEntity<List<Role>> listRoles() {
        List<Role> roles = authService.listRoles();
        return ResponseEntity.ok(roles);
    }




    // Endpoint pour supprimer un utilisateur
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/users/delete/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id) {
        MessageResponse messageResponse = authService.deleteUser(id);
        if (messageResponse.getMessage().startsWith("Error")) {
            return ResponseEntity.badRequest().body(messageResponse);
        }
        return ResponseEntity.ok(messageResponse);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/users/{userId}/activate")
    public ResponseEntity<MessageResponse> activateOrDeactivateUser(@PathVariable Long userId, @RequestParam boolean activate) {
        MessageResponse response = authService.activateOrDeactivateUser(userId, activate);
        return ResponseEntity.ok(response);
    }

}
