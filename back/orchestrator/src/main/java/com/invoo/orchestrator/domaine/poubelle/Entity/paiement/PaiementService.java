package com.invoo.orchestrator.domaine.poubelle.Entity.paiement;

import com.invoo.orchestrator.domaine.repository.IUserRepository;
import com.invoo.orchestrator.domaine.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaiementService {

    @Autowired
    private PaiementRepository paiementRepository;

    @Autowired
    private IUserRepository userRepository;

    public void ajouterPaiement(PaiementRequest paiementRequest, Authentication authentication) {
/*        if (authentication == null) {
            throw new RuntimeException("Authentication is null. User must be authenticated to perform this action.");
        }

        String clientEmail = authentication.getName();
        User client = userRepository.findByEmail(clientEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));

        User fournisseur = userRepository.findByEmail(paiementRequest.getFournisseurEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Fournisseur not found"));

        // Vérifier si le solde du client est suffisant
        if (client.getSolde() < paiementRequest.getMontant()) {
            throw new RuntimeException("Insufficient balance. Current balance: " + client.getSolde());
        }

        Paiement paiement = Paiement.builder()
                .montant(paiementRequest.getMontant())
                .client(client)
                .fournisseur(fournisseur)
                .datePaiement(new Timestamp(System.currentTimeMillis()))
                .build();

        paiementRepository.save(paiement);

        // Mettre à jour les soldes
        client.setSolde(client.getSolde() - paiementRequest.getMontant());
        fournisseur.setSolde(fournisseur.getSolde() + paiementRequest.getMontant());

        userRepository.save(client);
        userRepository.save(fournisseur);*/
    }

    public List<Paiement> getPaiementsByClient(User client) {
        return paiementRepository.findByClient(client);
    }
    public Paiement getPaiementById(Long id) {
        Optional<Paiement> paiement = paiementRepository.findById(id);
        if (paiement.isPresent()) {
            return paiement.get();
        } else {
            throw new RuntimeException("Paiement not found with id: " + id);
        }
    }
}
