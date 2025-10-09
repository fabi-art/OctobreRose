package com.example.grace.services;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
public class FileStorageService {

    @Value("${file.upload-dir:uploads/temoignages}")
    private String temoignageUploadDir;

    @Value("${file.tutoriel-upload-dir:uploads/tutoriels}")
    private String tutorielUploadDir;

    // Stocker un fichier de témoignage
    public String storeFile(MultipartFile file) {
        return storeFile(file, temoignageUploadDir, "/uploads/temoignages/");
    }

    // Stocker une vidéo de tutoriel
    public String storeTutorielVideo(MultipartFile file) {
        return storeFile(file, tutorielUploadDir, "/uploads/tutoriels/");
    }

    private String storeFile(MultipartFile file, String uploadDir, String urlPrefix) {
        try {
            // Créer le dossier s'il n'existe pas
            Path uploadPath = Paths.get(uploadDir);
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
            }

            // Générer un nom de fichier unique
            String originalFilename = file.getOriginalFilename();
            String fileExtension = originalFilename.substring(originalFilename.lastIndexOf("."));
            String newFilename = UUID.randomUUID().toString() + fileExtension;

            // Copier le fichier
            Path targetLocation = uploadPath.resolve(newFilename);
            Files.copy(file.getInputStream(), targetLocation, StandardCopyOption.REPLACE_EXISTING);

            return urlPrefix + newFilename;
        } catch (IOException ex) {
            throw new RuntimeException("Impossible de stocker le fichier. Erreur: " + ex.getMessage());
        }
    }

    public void deleteFile(String fileUrl) {
        try {
            if (fileUrl != null && !fileUrl.isEmpty()) {
                String filename = fileUrl.substring(fileUrl.lastIndexOf("/") + 1);
                
                // Déterminer le dossier en fonction de l'URL
                String uploadDir = fileUrl.contains("/temoignages/") 
                    ? temoignageUploadDir 
                    : tutorielUploadDir;
                
                Path filePath = Paths.get(uploadDir).resolve(filename);
                Files.deleteIfExists(filePath);
            }
        } catch (IOException ex) {
            throw new RuntimeException("Impossible de supprimer le fichier. Erreur: " + ex.getMessage());
        }
    }

    public String getFileType(MultipartFile file) {
        String contentType = file.getContentType();
        if (contentType == null) return "UNKNOWN";
        
        if (contentType.startsWith("image/")) return "IMAGE";
        if (contentType.startsWith("video/")) return "VIDEO";
        if (contentType.startsWith("audio/")) return "AUDIO";
        
        return "UNKNOWN";
    }
}