package com.invoo.orchestrator.domaine.poubelle.Entity.paiement;

import com.invoo.orchestrator.domaine.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/paiements")
public class PaiementController {

    @Autowired
    private PaiementService paiementService;
    private static final Logger logger = LoggerFactory.getLogger(PaiementController.class);
    @PostMapping("/send")
    public ResponseEntity<?> ajouterPaiement(@RequestBody PaiementRequest paiementRequest, Authentication authentication) {
        try {
            paiementService.ajouterPaiement(paiementRequest, authentication);
            return ResponseEntity.ok("Paiement ajouté avec succès");
        } catch (UsernameNotFoundException e) {
            logger.error("User not found", e);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("User not found: " + e.getMessage());
        } catch (RuntimeException e) {
            logger.error("Error during payment", e);
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            logger.error("Une erreur s'est produite lors de l'ajout du paiement", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de l'ajout du paiement");
        }
    }
    @GetMapping
    public List<Map<String, Object>> getPaiementsByClient(@AuthenticationPrincipal User authenticatedUser) {
        List<Paiement> paiements = paiementService.getPaiementsByClient(authenticatedUser);
        return paiements.stream().map(p -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", p.getId());
            map.put("montant", p.getMontant());
            map.put("client", p.getClient() != null ? p.getClient().getId() : null);
            map.put("fournisseur_id", p.getFournisseur() != null ? p.getFournisseur().getId() : null);
            map.put("fournisseur_name", p.getFournisseur() != null ? p.getFournisseur().getFirstname() : null);
            map.put("datePaiement", p.getDatePaiement());
            return map;
        }).collect(Collectors.toList());
    }


    @GetMapping("/{id}")
    public Paiement getPaiementById(@PathVariable Long id) {
        return paiementService.getPaiementById(id);
    }
}
