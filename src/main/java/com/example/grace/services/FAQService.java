package com.example.grace.services;

import com.example.grace.dto.FAQDTO;
import com.example.grace.entities.FAQ;
import com.example.grace.entities.User;
import com.example.grace.repositories.FAQRepository;
import com.example.grace.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FAQService {
    
    private final FAQRepository faqRepository;
    private final UserRepository userRepository;

    // Créer une FAQ
    @Transactional
    public FAQDTO createFAQ(FAQDTO faqDTO) {
        User user = userRepository.findById(faqDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        FAQ faq = new FAQ();
        faq.setQuestion(faqDTO.getQuestion());
        faq.setReponse(faqDTO.getReponse());
        faq.setCategorie(faqDTO.getCategorie());
        faq.setDescription(faqDTO.getDescription());
        faq.setTitre(faqDTO.getTitre());
        faq.setUser(user);
        
        FAQ savedFaq = faqRepository.save(faq);
        return convertToDTO(savedFaq);
    }

    // Récupérer toutes les FAQs
    public List<FAQDTO> getAllFAQs() {
        return faqRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Récupérer une FAQ par ID
    public FAQDTO getFAQById(Long id) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FAQ non trouvée"));
        return convertToDTO(faq);
    }

    // Récupérer FAQs par catégorie
    public List<FAQDTO> getFAQsByCategorie(String categorie) {
        return faqRepository.findByCategorie(categorie).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Rechercher FAQs par mot-clé
    public List<FAQDTO> searchFAQs(String keyword) {
        return faqRepository.findByQuestionContainingIgnoreCase(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Mettre à jour une FAQ
    @Transactional
    public FAQDTO updateFAQ(Long id, FAQDTO faqDTO) {
        FAQ faq = faqRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FAQ non trouvée"));
        
        faq.setQuestion(faqDTO.getQuestion());
        faq.setReponse(faqDTO.getReponse());
        faq.setCategorie(faqDTO.getCategorie());
        faq.setDescription(faqDTO.getDescription());
        faq.setTitre(faqDTO.getTitre());
        
        FAQ updatedFaq = faqRepository.save(faq);
        return convertToDTO(updatedFaq);
    }

    // Supprimer une FAQ
    @Transactional
    public void deleteFAQ(Long id) {
        if (!faqRepository.existsById(id)) {
            throw new RuntimeException("FAQ non trouvée");
        }
        faqRepository.deleteById(id);
    }

    // Convertir FAQ en DTO
    private FAQDTO convertToDTO(FAQ faq) {
        FAQDTO dto = new FAQDTO();
        dto.setId(faq.getId());
        dto.setQuestion(faq.getQuestion());
        dto.setReponse(faq.getReponse());
        dto.setCategorie(faq.getCategorie());
        dto.setDescription(faq.getDescription());
        dto.setTitre(faq.getTitre());
        dto.setUserId(faq.getUser().getId());
        return dto;
    }
}