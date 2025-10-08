package com.example.grace.web;

import com.example.grace.entities.Campagne;
import com.example.grace.repositories.CampagneRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/campagnes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class CampagneController {

    @Autowired
    private CampagneRepository campagneRepository;

    // 🧑‍💼 ADMIN crée une campagne
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<?> createCampagne(@RequestBody Campagne campagne) {
        return ResponseEntity.ok(campagneRepository.save(campagne));
    }

    // 👥 Tous les utilisateurs voient la liste des campagnes
    @GetMapping
    public ResponseEntity<List<Campagne>> getAllCampagnes() {
        return ResponseEntity.ok(campagneRepository.findAll());
    }

    // 👀 Voir une campagne en détail
    @GetMapping("/{id}")
    public ResponseEntity<Campagne> getCampagne(@PathVariable Long id) {
        return campagneRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
}
