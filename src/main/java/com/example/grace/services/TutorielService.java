package com.example.grace.services;

import com.example.grace.dto.TutorielDTO;
import com.example.grace.entities.Tutoriel;
import com.example.grace.entities.User;
import com.example.grace.repositories.TutorielRepository;
import com.example.grace.repositories.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class TutorielService {

    private final TutorielRepository tutorielRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    // === CREATE: vidéo OBLIGATOIRE ===
    @Transactional
    public TutorielDTO createTutoriel(TutorielDTO tutorielDTO, MultipartFile videoFile) {
        if (videoFile == null || videoFile.isEmpty()) {
            throw new RuntimeException("La vidéo est obligatoire à la création.");
        }

        User user = userRepository.findById(tutorielDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

        if (!isValidLangue(tutorielDTO.getLangue())) {
            throw new RuntimeException("Langue non supportée. Langues disponibles: fr, moore, dioula");
        }

        Tutoriel tutoriel = new Tutoriel();
        tutoriel.setTitre(tutorielDTO.getTitre());
        tutoriel.setContenu(tutorielDTO.getContenu());
        tutoriel.setDescription(tutorielDTO.getDescription());
        tutoriel.setLangue(tutorielDTO.getLangue());
        tutoriel.setDateCreation(new Date());
        tutoriel.setUser(user);

        // Upload vidéo (obligatoire ici)
        String videoUrl = fileStorageService.storeTutorielVideo(videoFile);
        tutoriel.setUrlVideo(videoUrl);

        Tutoriel savedTutoriel = tutorielRepository.save(tutoriel);
        return convertToDTO(savedTutoriel);
    }

    // READ: all
    public List<TutorielDTO> getAllTutoriels() {
        return tutorielRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // READ: by id
    public TutorielDTO getTutorielById(Long id) {
        Tutoriel tutoriel = tutorielRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutoriel non trouvé"));
        return convertToDTO(tutoriel);
    }

    // READ: by langue
    public List<TutorielDTO> getTutorielsByLangue(String langue) {
        if (!isValidLangue(langue)) {
            throw new RuntimeException("Langue non supportée. Langues disponibles: fr, moore, dioula");
        }
        return tutorielRepository.findByLangue(langue).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // SEARCH
    public List<TutorielDTO> searchTutoriels(String keyword) {
        return tutorielRepository.findByTitreContainingIgnoreCase(keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public List<TutorielDTO> searchByLangueAndKeyword(String langue, String keyword) {
        if (!isValidLangue(langue)) {
            throw new RuntimeException("Langue non supportée");
        }
        return tutorielRepository.findByLangueAndTitreContainingIgnoreCase(langue, keyword).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // === UPDATE: vidéo optionnelle (remplacement si fournie) ===
    @Transactional
    public TutorielDTO updateTutoriel(Long id, TutorielDTO tutorielDTO, MultipartFile videoFile) {
        Tutoriel tutoriel = tutorielRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutoriel non trouvé"));

        if (!isValidLangue(tutorielDTO.getLangue())) {
            throw new RuntimeException("Langue non supportée");
        }

        tutoriel.setTitre(tutorielDTO.getTitre());
        tutoriel.setContenu(tutorielDTO.getContenu());
        tutoriel.setDescription(tutorielDTO.getDescription());
        tutoriel.setLangue(tutorielDTO.getLangue());

        // Si une nouvelle vidéo est fournie -> remplace
        if (videoFile != null && !videoFile.isEmpty()) {
            fileStorageService.deleteFile(tutoriel.getUrlVideo());
            String videoUrl = fileStorageService.storeTutorielVideo(videoFile);
            tutoriel.setUrlVideo(videoUrl);
        }

        Tutoriel updatedTutoriel = tutorielRepository.save(tutoriel);
        return convertToDTO(updatedTutoriel);
    }

    // DELETE
    @Transactional
    public void deleteTutoriel(Long id) {
        Tutoriel tutoriel = tutorielRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Tutoriel non trouvé"));

        // Supprimer la vidéo associée
        fileStorageService.deleteFile(tutoriel.getUrlVideo());

        tutorielRepository.deleteById(id);
    }

    // Langues disponibles
    public List<String> getAvailableLanguages() {
        return List.of("fr", "moore", "dioula");
    }

    private boolean isValidLangue(String langue) {
        return langue != null &&
                (langue.equalsIgnoreCase("fr")
                        || langue.equalsIgnoreCase("moore")
                        || langue.equalsIgnoreCase("dioula"));
    }

    private TutorielDTO convertToDTO(Tutoriel tutoriel) {
        TutorielDTO dto = new TutorielDTO();
        dto.setId(tutoriel.getId());
        dto.setTitre(tutoriel.getTitre());
        dto.setContenu(tutoriel.getContenu());
        dto.setUrlVideo(tutoriel.getUrlVideo());
        dto.setDescription(tutoriel.getDescription());
        dto.setLangue(tutoriel.getLangue());
        dto.setDateCreation(tutoriel.getDateCreation());
        dto.setUserId(tutoriel.getUser().getId());
        return dto;
    }
}
