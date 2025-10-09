package com.example.grace.web;

import com.example.grace.dto.CentreSanteDTO;
import com.example.grace.entities.CentreSante;
import com.example.grace.services.CentreSanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

        import java.util.*;
        import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
@CrossOrigin("*")
public class CentreSanteController {

    @Autowired
    private CentreSanteService centreSanteService;

    // ✅ 1️⃣ Créer un centre de santé
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/centres/create")
    public ResponseEntity<CentreSanteDTO> createCentre(@RequestBody CentreSanteDTO centreDTO) {
        try {
            CentreSante centre = new CentreSante();
            centre.setNom(centreDTO.getNom());
            centre.setAdresse(centreDTO.getAdresse());
            centre.setVille(centreDTO.getVille());
            centre.setPays(centreDTO.getPays());
            centre.setTelephone(centreDTO.getTelephone());
            centre.setCoordonneeCentre(centreDTO.getCoordonneeCentre());

            centreSanteService.createCentreSante(centre);

            return ResponseEntity.status(HttpStatus.CREATED).body(new CentreSanteDTO(centre));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    // ✅ 2️⃣ Lister tous les centres
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/centres")
    public ResponseEntity<List<CentreSanteDTO>> getAllCentres() {
        List<CentreSante> centres = centreSanteService.getCentreSantes();
        List<CentreSanteDTO> centreDTOs = centres.stream()
                .map(CentreSanteDTO::new)
                .collect(Collectors.toList());
        return ResponseEntity.ok(centreDTOs);
    }

    // ✅ 3️⃣ Lister les centres avec pagination
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/centres/paging")
    public ResponseEntity<Map<String, Object>> getCentresPaged(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        try {
            Page<CentreSante> centrePage = centreSanteService.getCentreSantes(page, size);
            List<CentreSanteDTO> centreDTOs = centrePage.getContent()
                    .stream()
                    .map(CentreSanteDTO::new)
                    .collect(Collectors.toList());

            Map<String, Object> response = new HashMap<>();
            response.put("centres", centreDTOs);
            response.put("currentPage", centrePage.getNumber());
            response.put("totalItems", centrePage.getTotalElements());
            response.put("totalPages", centrePage.getTotalPages());

            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // ✅ 4️⃣ Récupérer un centre par ID
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/centres/{id}")
    public ResponseEntity<CentreSanteDTO> getCentreById(@PathVariable long id) {
        try {
            CentreSante centre = centreSanteService.getOneCentreSante(id);
            return ResponseEntity.ok(new CentreSanteDTO(centre));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ 5️⃣ Supprimer un centre
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/centres/delete/{id}")
    public ResponseEntity<Void> deleteCentre(@PathVariable long id) {
        try {
            centreSanteService.delete(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ 6️⃣ Mettre à jour un centre
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/centres/update/{id}")
    public ResponseEntity<CentreSanteDTO> updateCentre(
            @RequestBody CentreSanteDTO centreDTO,
            @PathVariable long id) {
        try {
            CentreSante centre = new CentreSante();
            centre.setNom(centreDTO.getNom());
            centre.setAdresse(centreDTO.getAdresse());
            centre.setVille(centreDTO.getVille());
            centre.setPays(centreDTO.getPays());
            centre.setTelephone(centreDTO.getTelephone());
            centre.setCoordonneeCentre(centreDTO.getCoordonneeCentre());

            CentreSante updatedCentre = centreSanteService.updateSite(centre, id);
            return ResponseEntity.ok(new CentreSanteDTO(updatedCentre));
        } catch (Exception e) {
            return ResponseEntity.notFound().build();
        }
    }

    // ✅ 7️⃣ Recherche par ville ou nom (utile pour filtrer dans la carte interactive)
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/centres/search")
    public ResponseEntity<List<CentreSanteDTO>> searchCentres(
            @RequestParam(required = false) String ville,
            @RequestParam(required = false) String nom) {
        List<CentreSante> centres;

        if (ville != null && nom != null) {
            centres = centreSanteService.getCentreSantes()
                    .stream()
                    .filter(c -> c.getVille().equalsIgnoreCase(ville)
                            && c.getNom().toLowerCase().contains(nom.toLowerCase()))
                    .collect(Collectors.toList());
        } else if (ville != null) {
            centres = centreSanteService.getCentreSantes()
                    .stream()
                    .filter(c -> c.getVille().equalsIgnoreCase(ville))
                    .collect(Collectors.toList());
        } else if (nom != null) {
            centres = centreSanteService.getCentreSantes()
                    .stream()
                    .filter(c -> c.getNom().toLowerCase().contains(nom.toLowerCase()))
                    .collect(Collectors.toList());
        } else {
            centres = centreSanteService.getCentreSantes();
        }

        List<CentreSanteDTO> centreDTOs = centres.stream()
                .map(CentreSanteDTO::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(centreDTOs);
    }
}
