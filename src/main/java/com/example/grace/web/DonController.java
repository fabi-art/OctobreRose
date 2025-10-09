package com.example.grace.web;

import com.example.grace.entities.Campagne;
import com.example.grace.entities.Don;
import com.example.grace.entities.User;
import com.example.grace.repositories.CampagneRepository;
import com.example.grace.repositories.DonRepository;
import com.example.grace.repositories.UserRepository;
import com.example.grace.services.UserDetailsImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/dons")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DonController {

    @Autowired
    private DonRepository donRepository;

    @Autowired
    private CampagneRepository campagneRepository;

    @Autowired
    private UserRepository userRepository;

    // Créer un don (connecté ou anonyme)
    @PostMapping("/campagne/{campagneId}")
    public ResponseEntity<?> creerDon(
            @PathVariable Long campagneId,
            @RequestBody Don don,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        Campagne campagne = campagneRepository.findById(campagneId)
                .orElseThrow(() -> new RuntimeException("Campagne non trouvée"));

        // Si l'utilisateur est connecté
        if (userDetails != null) {
            User user = userRepository.findById(userDetails.getId())
                    .orElseThrow(() -> new RuntimeException("Utilisateur non trouvé"));
            don.setUser(user);
            don.setAnonyme(false);
            if (don.getNomDonateur() == null) {
                don.setNomDonateur(user.getNom());
            }
        } else {
            // Si don anonyme
            don.setAnonyme(true);
        }

        don.setCampagne(campagne);
        don.setPaiementEffectue(false); // par défaut

        Don savedDon = donRepository.save(don);
        return ResponseEntity.ok(savedDon);
    }

    // 🔹Simuler un paiement validé
    @PutMapping("/{donId}/payer")
    public ResponseEntity<?> validerPaiement(@PathVariable Long donId) {
        Don don = donRepository.findById(donId)
                .orElseThrow(() -> new RuntimeException("Don non trouvé"));

        if (don.isPaiementEffectue()) {
            return ResponseEntity.badRequest().body("Ce don est déjà payé !");
        }

        don.setPaiementEffectue(true);
        donRepository.save(don);

        // Mettre à jour le montant total de la campagne
        Campagne campagne = don.getCampagne();
        double total = campagne.getDons().stream()
                .filter(Don::isPaiementEffectue)
                .mapToDouble(Don::getMontant)
                .sum();
        campagne.setMontantCollecte(total);
        campagneRepository.save(campagne);

        return ResponseEntity.ok("Paiement validé et campagne mise à jour ✅");
    }

    // Voir tous les dons d’une campagne
    @GetMapping("/campagne/{campagneId}")
    public ResponseEntity<List<Don>> getDonsByCampagne(@PathVariable Long campagneId) {
        Campagne campagne = campagneRepository.findById(campagneId)
                .orElseThrow(() -> new RuntimeException("Campagne non trouvée"));
        List<Don> dons = donRepository.findByCampagne(campagne);
        return ResponseEntity.ok(dons);
    }
}
