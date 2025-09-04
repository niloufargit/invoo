package com.invoo.orchestrator.domaine.poubelle.Entity.transactionRequest;


import com.invoo.orchestrator.domaine.poubelle.Entity.paiement.PaiementController;
import com.invoo.orchestrator.domaine.entity.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/api/transaction-requests")
public class TransactionRequestController {
    private static final Logger logger = LoggerFactory.getLogger(PaiementController.class);
    @Autowired
    private TransactionRequestService transactionRequestService;

    @PostMapping
    public ResponseEntity<?> createTransactionRequest(@RequestBody TransactionRequest transactionRequest, Authentication authentication) {
        try {
            transactionRequestService.createTransactionRequest(transactionRequest,authentication);
            return ResponseEntity.ok("Paiement ajouté avec succès");
        } catch (Exception e) {
            logger.error("Une erreur s'est produite lors de la demande du paiement", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Erreur lors de la demande du paiement");
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Transaction> updateTransactionStatus(@PathVariable Long id, @RequestParam TransactionStatus newStatus) {
        Transaction updatedTransaction = transactionRequestService.updateTransactionStatus(id, newStatus);
        return ResponseEntity.ok(updatedTransaction);
    }

    @GetMapping
    public List<Map<String, Object>> getAllTransactionRequests(@AuthenticationPrincipal User authenticatedUser) {
        return transactionRequestService.getAllTransactionRequests(authenticatedUser);
    }

    @GetMapping("getRequests")
    public List<Map<String, Object>> getAllTransactionToBenef(@AuthenticationPrincipal User authenticatedUser) {
        return transactionRequestService.getAllTransactionToBenef(authenticatedUser);
    }
    @GetMapping("/{id}")
    public ResponseEntity<Transaction> getTransactionRequestById(@PathVariable Long id) {
        Optional<Transaction> transactionRequest = transactionRequestService.getTransactionRequestById(id);
        return transactionRequest.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTransactionRequest(@PathVariable Long id) {
        transactionRequestService.deleteTransactionRequest(id);
        return ResponseEntity.noContent().build();
    }
}
