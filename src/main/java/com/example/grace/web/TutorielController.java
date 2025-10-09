package com.example.grace.web;

import com.example.grace.dto.TutorielDTO;
import com.example.grace.services.TutorielService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api/tutoriels")
@RequiredArgsConstructor
@CrossOrigin(origins = "*", maxAge = 3600)
public class TutorielController {

    private final TutorielService tutorielService;

    // === CREATE (vidéo OBLIGATOIRE) ===
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TutorielDTO> createTutoriel(
            @RequestPart("data") @Valid TutorielDTO tutorielDTO,
            @RequestPart("video") MultipartFile videoFile
    ) {
        try {
            if (videoFile == null || videoFile.isEmpty()) {
                return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
            }
            TutorielDTO createdTutoriel = tutorielService.createTutoriel(tutorielDTO, videoFile);
            return new ResponseEntity<>(createdTutoriel, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    // === UPDATE (vidéo REMPLAÇABLE, optionnelle) ===
    @PutMapping(value = "/{id}", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<TutorielDTO> updateTutoriel(
            @PathVariable Long id,
            @RequestPart("data") @Valid TutorielDTO tutorielDTO,
            @RequestPart(value = "video", required = false) MultipartFile videoFile
    ) {
        try {
            TutorielDTO updatedTutoriel = tutorielService.updateTutoriel(id, tutorielDTO, videoFile);
            return new ResponseEntity<>(updatedTutoriel, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // === READS ===
    @GetMapping("/admin/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<TutorielDTO>> getAllTutoriels() {
        try {
            List<TutorielDTO> tutoriels = tutorielService.getAllTutoriels();
            if (tutoriels.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(tutoriels, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/langue/{langue}")
    public ResponseEntity<List<TutorielDTO>> getTutorielsByLangue(@PathVariable String langue) {
        try {
            List<TutorielDTO> tutoriels = tutorielService.getTutorielsByLangue(langue);
            if (tutoriels.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(tutoriels, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<TutorielDTO> getTutorielById(@PathVariable Long id) {
        try {
            TutorielDTO tutoriel = tutorielService.getTutorielById(id);
            return new ResponseEntity<>(tutoriel, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/search")
    public ResponseEntity<List<TutorielDTO>> searchTutoriels(@RequestParam String keyword) {
        try {
            List<TutorielDTO> tutoriels = tutorielService.searchTutoriels(keyword);
            if (tutoriels.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(tutoriels, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/search/langue/{langue}")
    public ResponseEntity<List<TutorielDTO>> searchByLangueAndKeyword(
            @PathVariable String langue,
            @RequestParam String keyword) {
        try {
            List<TutorielDTO> tutoriels = tutorielService.searchByLangueAndKeyword(langue, keyword);
            if (tutoriels.isEmpty()) return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            return new ResponseEntity<>(tutoriels, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.BAD_REQUEST);
        }
    }

    @GetMapping("/langues")
    public ResponseEntity<List<String>> getAvailableLanguages() {
        List<String> langues = tutorielService.getAvailableLanguages();
        return new ResponseEntity<>(langues, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteTutoriel(@PathVariable Long id) {
        try {
            tutorielService.deleteTutoriel(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
