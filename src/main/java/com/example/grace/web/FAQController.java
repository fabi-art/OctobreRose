package com.example.grace.web;

import com.example.grace.dto.FAQDTO;
import com.example.grace.services.FAQService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/faq")
@RequiredArgsConstructor
public class FAQController {
    
    private final FAQService faqService;

    // Créer une FAQ (admin seulement)
    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FAQDTO> createFAQ(@RequestBody FAQDTO faqDTO) {
        try {
            FAQDTO createdFAQ = faqService.createFAQ(faqDTO);
            return new ResponseEntity<>(createdFAQ, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Récupérer toutes les FAQs (accessible à tous)
    @GetMapping
    public ResponseEntity<List<FAQDTO>> getAllFAQs() {
        try {
            List<FAQDTO> faqs = faqService.getAllFAQs();
            if (faqs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(faqs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Récupérer une FAQ par ID
    @GetMapping("/{id}")
    public ResponseEntity<FAQDTO> getFAQById(@PathVariable Long id) {
        try {
            FAQDTO faq = faqService.getFAQById(id);
            return new ResponseEntity<>(faq, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Récupérer FAQs par catégorie
    @GetMapping("/categorie/{categorie}")
    public ResponseEntity<List<FAQDTO>> getFAQsByCategorie(@PathVariable String categorie) {
        try {
            List<FAQDTO> faqs = faqService.getFAQsByCategorie(categorie);
            if (faqs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(faqs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Rechercher FAQs par mot-clé
    @GetMapping("/search")
    public ResponseEntity<List<FAQDTO>> searchFAQs(@RequestParam String keyword) {
        try {
            List<FAQDTO> faqs = faqService.searchFAQs(keyword);
            if (faqs.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
            return new ResponseEntity<>(faqs, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Mettre à jour une FAQ (admin seulement)
    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<FAQDTO> updateFAQ(@PathVariable Long id, @RequestBody FAQDTO faqDTO) {
        try {
            FAQDTO updatedFAQ = faqService.updateFAQ(id, faqDTO);
            return new ResponseEntity<>(updatedFAQ, HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
        }
    }

    // Supprimer une FAQ (admin seulement)
    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<HttpStatus> deleteFAQ(@PathVariable Long id) {
        try {
            faqService.deleteFAQ(id);
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}