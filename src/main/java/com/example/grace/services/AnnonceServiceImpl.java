package com.example.grace.services;

import com.example.grace.entities.Annonce;
import com.example.grace.entities.User;
import com.example.grace.exceptions.InvalidModelException;
import com.example.grace.repositories.AnnonceRepository;
import com.example.grace.repositories.CentreSanteRepository;
import com.example.grace.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class AnnonceServiceImpl implements AnnonceService {

    @Autowired
    private AnnonceRepository annonceRepository;

    @Autowired
    private CentreSanteRepository centreSanteRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private NotificationService notificationService; // 🔔 (à créer juste après)

    // ✅ Créer une annonce et notifier les utilisateurs
    @Override
    public void createAnnonce(Annonce annonce) {
        try {
            if (annonce.getTitreAnnonce() == null || annonce.getTitreAnnonce().isEmpty()) {
                throw new InvalidModelException("Le titre de l’annonce est obligatoire.");
            }

            if (annonce.getDescAnnonce() == null || annonce.getDescAnnonce().isEmpty()) {
                throw new InvalidModelException("Le description de l’annonce est obligatoire.");
            }

            if (annonce.getTypeAnnonce() == null || annonce.getTypeAnnonce().isEmpty()) {
                throw new InvalidModelException("Le titre de l’annonce est obligatoire.");
            }

            if (annonce.getCentreSante() == null) {
                throw new InvalidModelException("L’annonce doit être liée à un centre de santé.");
            }

            // Récupération de l’utilisateur connecté
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            String username = auth.getName();
            User currentUser = userRepository.findByPseudo(username)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            annonce.setUser(currentUser);

            // Sauvegarde
            Annonce savedAnnonce = annonceRepository.save(annonce);

            // ✅ Notification automatique
            String ville = savedAnnonce.getCentreSante().getVille();
            String message = "🩺 Nouvelle annonce : " + savedAnnonce.getTitreAnnonce() +
                    "\nType : " + savedAnnonce.getTypeAnnonce() +
                    "\nDescription : " + savedAnnonce.getDescAnnonce() +
                    "\nCentre : " + savedAnnonce.getCentreSante().getNom() +
                    "\nCoordonnees : " + savedAnnonce.getCentreSante().getCoordonneeCentre();

            notificationService.notifyUsersInCity(ville, message);

        } catch (Exception e) {
            throw new RuntimeException("Erreur lors de la création de l’annonce : " + e.getMessage());
        }
    }

    @Override
    public List<Annonce> getAnnonces() {
        return annonceRepository.findAll();
    }

    @Override
    public Annonce getAnnonceById(long id) {
        return annonceRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Annonce introuvable"));
    }

    @Override
    public void deleteAnnonce(long id) {
        annonceRepository.deleteById(id);
    }

    @Override
    public Annonce updateAnnonce(Annonce annonce, long id) {
        Annonce existing = getAnnonceById(id);
        existing.setTitreAnnonce(annonce.getTitreAnnonce());
        existing.setDescAnnonce(annonce.getDescAnnonce());
        existing.setTypeAnnonce(annonce.getTypeAnnonce());
        return annonceRepository.save(existing);
    }

    @Override
    public List<Annonce> searchByType(String typeAnnonce) {
        return annonceRepository.findByTypeAnnonceContainingIgnoreCase(typeAnnonce);
    }

    @Override
    public List<Annonce> getAnnoncesByVille(String ville) {
        return annonceRepository.findByCentreSante_VilleIgnoreCase(ville);
    }
}
