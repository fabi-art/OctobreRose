package com.example.grace.web;

import com.example.grace.dto.TemoignageDTO;
import com.example.grace.services.TemoignageService;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/temoignages")
@RequiredArgsConstructor
public class TemoignageController {
    
    private final TemoignageService temoignageService;
    private final ObjectMapper objectMapper;

    // Créer un témoignage avec média (Admin seulement)
    @PostMapping(consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TemoignageDTO> createTemoignage(
            @RequestPart("temoignage") String temoignageJson,
            @RequestPart(value = "media", required = false) MultipartFile mediaFile) {
        try {
            TemoignageDTO temoignageDTO = objectMapper.readValue(temoignageJson, TemoignageDTO.class);
            TemoignageDTO createdTemoignage = temoignageService.createTemoignage(temoignageDTO, mediaFile);
            return new ResponseEntity<>(createdTemoignage, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Récupérer tous les témoignages (Admin seulement)
    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TemoignageDTO>> getAllTemoignages() {
        try {
            List<TemoignageDTO> temoignages = temoignageService.getAllTemoignages();
            if (temoignages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(temoignages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Récupérer uniquement les témoignages validés (Public)
    @GetMapping("/validated")
    public ResponseEntity<List<TemoignageDTO>> getValidatedTemoignages() {
        try {
            List<TemoignageDTO> temoignages = temoignageService.getValidatedTemoignages();
            if (temoignages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(temoignages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Récupérer un témoignage par ID
    @GetMapping("/{id}")
    public ResponseEntity<TemoignageDTO> getTemoignageById(@PathVariable Long id) {
        try {
            TemoignageDTO temoignage = temoignageService.getTemoignageById(id);
            return new ResponseEntity<>(temoignage, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Récupérer par type de média
    @GetMapping("/type/{type}")
    public ResponseEntity<List<TemoignageDTO>> getTemoignagesByType(@PathVariable String type) {
        try {
            List<TemoignageDTO> temoignages = temoignageService.getTemoignagesByType(type);
            if (temoignages.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(temoignages, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mettre à jour un témoignage (Admin seulement)
    @PutMapping(value = "/{id}", consumes = {"multipart/form-data"})
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TemoignageDTO> updateTemoignage(
            @PathVariable Long id,
            @RequestPart("temoignage") String temoignageJson,
            @RequestPart(value = "media", required = false) MultipartFile mediaFile) {
        try {
            TemoignageDTO temoignageDTO = objectMapper.readValue(temoignageJson, TemoignageDTO.class);
            TemoignageDTO updatedTemoignage = temoignageService.updateTemoignage(id, temoignageDTO, mediaFile);
            return new ResponseEntity<>(updatedTemoignage, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Valider/Invalider un témoignage (Admin seulement)
    @PatchMapping("/{id}/toggle-statut")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TemoignageDTO> toggleStatut(@PathVariable Long id) {
        try {
            TemoignageDTO temoignage = temoignageService.toggleStatut(id);
            return new ResponseEntity<>(temoignage, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Supprimer un témoignage (Admin seulement)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteTemoignage(@PathVariable Long id) {
        try {
            temoignageService.deleteTemoignage(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}