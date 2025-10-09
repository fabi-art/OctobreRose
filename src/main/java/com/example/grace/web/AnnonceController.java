package com.example.grace.web;




import com.example.grace.dto.AnnonceDTO;
import com.example.grace.entities.Annonce;
import com.example.grace.entities.CentreSante;
import com.example.grace.services.AnnonceService;
import com.example.grace.services.CentreSanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
        import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

        import java.util.*;
        import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class AnnonceController {

    @Autowired
    private AnnonceService annonceService;

    @Autowired
    private CentreSanteService centreSanteService;

    // ✅ 1️⃣ Créer une annonce (accessible à tous les utilisateurs connectés)
    @PreAuthorize("isAuthenticated() and hasRole('ROLE_ADMIN')")
    @PostMapping("/annonces/create")
    public ResponseEntity<?> createAnnonce(@RequestBody AnnonceDTO annonceDTO) {
        try {
            Annonce annonce = new Annonce();
            annonce.setTitreAnnonce(annonceDTO.getTitreAnnonce());
            annonce.setDescAnnonce(annonceDTO.getDescAnnonce());
            annonce.setTypeAnnonce(annonceDTO.getTypeAnnonce());
            CentreSante cs = centreSanteService.getOneCentreSante(annonceDTO.getCentreSanteId());
            if (cs == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Fournisseur non trouvé
            }
            annonce.setCentreSante(cs);

            annonceService.createAnnonce(annonce);
            return ResponseEntity.status(HttpStatus.CREATED).body("✅ Annonce créée avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de la création : " + e.getMessage());
        }
    }

    // ✅ 2️⃣ Lister toutes les annonces
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/annonces")
    public ResponseEntity<List<AnnonceDTO>> getAllAnnonces() {
        List<Annonce> annonces = annonceService.getAnnonces();
        List<AnnonceDTO> annonceDTOs = annonces.stream()
                .map(AnnonceDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(annonceDTOs);
    }

    // ✅ 3️⃣ Lister les annonces avec pagination
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/annonces/paging")
    public ResponseEntity<Map<String, Object>> getAnnoncesPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            // ⚠️ Si tu veux une vraie pagination, il faut l’ajouter dans ton service
            // Ici on simule avec une simple sous-liste
            List<Annonce> all = annonceService.getAnnonces();
            int start = Math.min(page * size, all.size());
            int end = Math.min(start + size, all.size());
            List<AnnonceDTO> annonceDTOs = all.subList(start, end)
                    .stream()
                    .map(AnnonceDTO::new)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("annonces", annonceDTOs);
            response.put("currentPage", page);
            response.put("totalItems", all.size());
            response.put("totalPages", (int) Math.ceil((double) all.size() / size));

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    // ✅ 4️⃣ Récupérer une annonce par ID
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/annonces/{id}")
    public ResponseEntity<?> getAnnonceById(@PathVariable long id) {
        try {
            Annonce annonce = annonceService.getAnnonceById(id);
            return ResponseEntity.ok(new AnnonceDTO(annonce));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Annonce introuvable : " + e.getMessage());
        }
    }

    // ✅ 5️⃣ Supprimer une annonce
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/annonces/delete/{id}")
    public ResponseEntity<?> deleteAnnonce(@PathVariable long id) {
        try {
            annonceService.deleteAnnonce(id);
            return ResponseEntity.ok("🗑️ Annonce supprimée avec succès !");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Erreur lors de la suppression : " + e.getMessage());
        }
    }

    // ✅ 6️⃣ Mettre à jour une annonce
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/annonces/update/{id}")
    public ResponseEntity<?> updateAnnonce(
            @RequestBody AnnonceDTO annonceDTO,
            @PathVariable long id) {
        try {
            Annonce annonce = new Annonce();
            annonce.setTitreAnnonce(annonceDTO.getTitreAnnonce());
            annonce.setDescAnnonce(annonceDTO.getDescAnnonce());
            annonce.setTypeAnnonce(annonceDTO.getTypeAnnonce());
            CentreSante cs = centreSanteService.getOneCentreSante(annonceDTO.getCentreSanteId());
            if (cs == null) {
                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null); // Fournisseur non trouvé
            }
            annonce.setCentreSante(cs);

            Annonce updatedAnnonce = annonceService.updateAnnonce(annonce, id);
            return ResponseEntity.ok(new AnnonceDTO(updatedAnnonce));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body("Erreur lors de la mise à jour : " + e.getMessage());
        }
    }

    // ✅ 7️⃣ Recherche d’annonces par type
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/annonces/search")
    public ResponseEntity<List<AnnonceDTO>> searchByType(@RequestParam String type) {
        List<Annonce> annonces = annonceService.searchByType(type);
        List<AnnonceDTO> annonceDTOs = annonces.stream()
                .map(AnnonceDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(annonceDTOs);
    }

    // ✅ 8️⃣ Récupérer les annonces d’une ville donnée
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/annonces/ville/{ville}")
    public ResponseEntity<List<AnnonceDTO>> getAnnoncesByVille(@PathVariable String ville) {
        List<Annonce> annonces = annonceService.getAnnoncesByVille(ville);
        List<AnnonceDTO> annonceDTOs = annonces.stream()
                .map(AnnonceDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(annonceDTOs);
    }

    // ✅ 9️⃣ Filtrer par ville + type (utile pour interface utilisateur)
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/annonces/filter")
    public ResponseEntity<List<AnnonceDTO>> filterAnnonces(
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) String type) {

        List<Annonce> annonces = annonceService.getAnnonces();

        if (ville != null && !ville.isEmpty()) {
            annonces = annonces.stream()
                    .filter(a -> a.getCentreSante().getVille().equalsIgnoreCase(ville))
                    .collect(Collectors.toList());
        }

        if (type != null && !type.isEmpty()) {
            annonces = annonces.stream()
                    .filter(a -> a.getTypeAnnonce().equalsIgnoreCase(type))
                    .collect(Collectors.toList());
        }

        List<AnnonceDTO> annonceDTOs = annonces.stream()
                .map(AnnonceDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(annonceDTOs);
    }
}
