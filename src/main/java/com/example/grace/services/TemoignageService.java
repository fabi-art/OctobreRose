package com.example.grace.services;

import com.example.grace.dto.TemoignageDTO;
import com.example.grace.entities.Temoignage;
import com.example.grace.entities.User;
import com.example.grace.repositories.TemoignageRepository;
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
public class TemoignageService {
    
    private final TemoignageRepository temoignageRepository;
    private final UserRepository userRepository;
    private final FileStorageService fileStorageService;

    // Créer un témoignage avec média
    @Transactional
    public TemoignageDTO createTemoignage(TemoignageDTO temoignageDTO, MultipartFile mediaFile) {
        User user = userRepository.findById(temoignageDTO.getUserId())
                .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
        
        Temoignage temoignage = new Temoignage();
        temoignage.setContenu(temoignageDTO.getContenu());
        temoignage.setDateTemoignage(new Date());
        temoignage.setTitreTemoignage(temoignageDTO.getTitreTemoignage());
        temoignage.setDescriptionTemoignage(temoignageDTO.getDescriptionTemoignage());
        temoignage.setStatut(false); // Par défaut, non validé
        temoignage.setUser(user);

        // Gérer le fichier média si présent
        if (mediaFile != null && !mediaFile.isEmpty()) {
            String mediaUrl = fileStorageService.storeFile(mediaFile);
            String fileType = fileStorageService.getFileType(mediaFile);
            temoignage.setContenu(mediaUrl); // Stocker l'URL du média dans contenu
            temoignage.setType(fileType);
        }
        
        Temoignage savedTemoignage = temoignageRepository.save(temoignage);
        return convertToDTO(savedTemoignage);
    }

    // Récupérer tous les témoignages
    public List<TemoignageDTO> getAllTemoignages() {
        return temoignageRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Récupérer uniquement les témoignages validés
    public List<TemoignageDTO> getValidatedTemoignages() {
        return temoignageRepository.findByStatutTrue().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Récupérer un témoignage par ID
    public TemoignageDTO getTemoignageById(Long id) {
        Temoignage temoignage = temoignageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Témoignage non trouvé"));
        return convertToDTO(temoignage);
    }

    // Récupérer par type de média
    public List<TemoignageDTO> getTemoignagesByType(String type) {
        return temoignageRepository.findByType(type).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Mettre à jour un témoignage
    @Transactional
    public TemoignageDTO updateTemoignage(Long id, TemoignageDTO temoignageDTO, MultipartFile mediaFile) {
        Temoignage temoignage = temoignageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Témoignage non trouvé"));
        
        temoignage.setTitreTemoignage(temoignageDTO.getTitreTemoignage());
        temoignage.setDescriptionTemoignage(temoignageDTO.getDescriptionTemoignage());
        
        // Si un nouveau média est fourni
        if (mediaFile != null && !mediaFile.isEmpty()) {
            // Supprimer l'ancien fichier
            fileStorageService.deleteFile(temoignage.getContenu());
            
            // Stocker le nouveau
            String mediaUrl = fileStorageService.storeFile(mediaFile);
            String fileType = fileStorageService.getFileType(mediaFile);
            temoignage.setContenu(mediaUrl);
            temoignage.setType(fileType);
        }
        
        Temoignage updatedTemoignage = temoignageRepository.save(temoignage);
        return convertToDTO(updatedTemoignage);
    }

    // Valider/Invalider un témoignage (Admin)
    @Transactional
    public TemoignageDTO toggleStatut(Long id) {
        Temoignage temoignage = temoignageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Témoignage non trouvé"));
        
        temoignage.setStatut(!temoignage.getStatut());
        Temoignage updatedTemoignage = temoignageRepository.save(temoignage);
        return convertToDTO(updatedTemoignage);
    }

    // Supprimer un témoignage
    @Transactional
    public void deleteTemoignage(Long id) {
        Temoignage temoignage = temoignageRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Témoignage non trouvé"));
        
        // Supprimer le fichier média associé
        fileStorageService.deleteFile(temoignage.getContenu());
        
        temoignageRepository.deleteById(id);
    }

    // Convertir Temoignage en DTO
    private TemoignageDTO convertToDTO(Temoignage temoignage) {
        TemoignageDTO dto = new TemoignageDTO();
        dto.setId(temoignage.getId());
        dto.setContenu(temoignage.getContenu());
        dto.setDateTemoignage(temoignage.getDateTemoignage());
        dto.setTitreTemoignage(temoignage.getTitreTemoignage());
        dto.setDescriptionTemoignage(temoignage.getDescriptionTemoignage());
        dto.setType(temoignage.getType());
        dto.setStatut(temoignage.getStatut());
        dto.setMediaUrl(temoignage.getContenu()); // URL du média
        dto.setUserId(temoignage.getUser().getId());
        return dto;
    }
}