package com.example.grace.services;

import com.example.grace.entities.CentreSante;
import com.example.grace.repositories.CentreSanteRepository;
import com.example.grace.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import com.example.grace.entities.User;
import com.example.grace.exceptions.*;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

@Service
public class CentreSanteServiceImpl implements CentreSanteService {

    @Autowired
    private CentreSanteRepository centreSanteRepository;

    @Autowired
    private UserRepository userRepository;

    // ✅ Créer un centre de santé
    @Override
    public void createCentreSante(CentreSante model) {
        try {
            // Validation des champs obligatoires
            if (model.getNom() == null || model.getNom().isEmpty()) {
                throw new InvalidModelException("Le nom du centre de santé ne peut pas être vide");
            }
            if (model.getVille() == null || model.getVille().isEmpty()) {
                throw new InvalidModelException("La ville du centre de santé ne peut pas être vide");
            }

            if (model.getCoordonneeCentre() == null || model.getCoordonneeCentre().isEmpty()) {
                throw new InvalidModelException("Les coordonnees du centre de santé ne peut pas être vide");
            }

            if (model.getAdresse()== null || model.getAdresse().isEmpty()) {
                throw new InvalidModelException("L adresse du centre de santé ne peut pas être vide");
            }

            if (model.getPays() == null || model.getPays().isEmpty()) {
                throw new InvalidModelException("Le pays dorigine ne peut pas être vide");
            }

            if (model.getTelephone() == null || model.getTelephone().isEmpty()) {
                throw new InvalidModelException("Le telephone ne peut pas être vide");
            }


            // Vérifier si un centre avec le même nom et la même ville existe déjà
            if (centreSanteRepository.existsByNomAndVille(model.getNom(), model.getVille())) {
                throw new DuplicateException("Un centre de santé avec le même nom et la même ville existe déjà");
            }

            // Récupérer l’utilisateur actuellement connecté
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            User currentUser = userRepository.findByPseudo(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Associer l’utilisateur au centre de santé
            model.setUser(currentUser);

            // Sauvegarder dans la base de données
            this.centreSanteRepository.save(model);

        } catch (InvalidModelException | DuplicateException e) {
            throw e;
        }
    }

    // ✅ Récupérer tous les centres de santé
    @Override
    public List<CentreSante> getCentreSantes() {
        try {
            return this.centreSanteRepository.findAll();
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération des centres : " + e.getMessage());
            throw new RuntimeException("Impossible de récupérer les centres", e);
        }
    }

    // ✅ Pagination
    @Override
    public Page<CentreSante> getCentreSantes(int page, int size) {
        try {
            Pageable pageable = PageRequest.of(page, size);
            return centreSanteRepository.findAll(pageable);
        } catch (Exception e) {
            System.err.println("Erreur lors de la récupération paginée : " + e.getMessage());
            throw new RuntimeException("Échec de la récupération paginée", e);
        }
    }

    // ✅ Récupérer un centre par ID
    @Override
    public CentreSante getOneCentreSante(long id) {
        try {
            return this.centreSanteRepository.findById(id)
                    .orElseThrow(() -> new CentreSanteNotFoundException("Centre de santé avec ID " + id + " non trouvé"));
        } catch (CentreSanteNotFoundException e) {
            throw e;
        }
    }

    // ✅ Supprimer un centre de santé
    @Override
    public void delete(long id) {
        try {
            if (!centreSanteRepository.existsById(id)) {
                throw new CentreSanteNotFoundException("Centre de santé avec ID " + id + " non trouvé");
            }

            centreSanteRepository.deleteById(id);
        } catch (CentreSanteNotFoundException e) {
            throw e;
        } catch (Exception e) {
            System.err.println("Erreur lors de la suppression du centre : " + e.getMessage());
            throw new RuntimeException("Impossible de supprimer le centre", e);
        }
    }

    // ✅ Mettre à jour un centre de santé
    @Override
    public CentreSante updateSite(CentreSante model, Long id) {
        try {
            if (model.getNom() == null || model.getNom().isEmpty()) {
                throw new InvalidModelException("Le nom du centre ne peut pas être vide");
            }
            if (model.getVille() == null || model.getVille().isEmpty()) {
                throw new InvalidModelException("La ville ne peut pas être vide");
            }

            CentreSante centre = centreSanteRepository.findById(id)
                    .orElseThrow(() -> new CentreSanteNotFoundException("Centre avec ID " + id + " non trouvé"));

            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String currentUsername = authentication.getName();
            User currentUser = userRepository.findByPseudo(currentUsername)
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));

            // Mise à jour des champs
            centre.setNom(model.getNom());
            centre.setAdresse(model.getAdresse());
            centre.setVille(model.getVille());
            centre.setPays(model.getPays());
            centre.setTelephone(model.getTelephone());
            centre.setCoordonneeCentre(model.getCoordonneeCentre());
            centre.setUser(currentUser);

            return this.centreSanteRepository.save(centre);

        } catch (InvalidModelException | CentreSanteNotFoundException e) {
            throw e;
        }
    }

    // ✅ Pour un retour paginé personnalisé (optionnel)
    @Override
    public Map<String, Object> findAllCentreSantes(int page, int size) {
        return Map.of();
    }
}
