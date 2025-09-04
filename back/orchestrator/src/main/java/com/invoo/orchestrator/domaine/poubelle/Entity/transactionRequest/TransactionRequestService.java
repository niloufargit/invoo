package com.invoo.orchestrator.domaine.poubelle.Entity.transactionRequest;

import com.invoo.orchestrator.domaine.poubelle.Entity.facture.Facture;
import com.invoo.orchestrator.domaine.poubelle.Entity.facture.FactureRepository;
import com.invoo.orchestrator.domaine.repository.IUserRepository;
import com.invoo.orchestrator.domaine.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TransactionRequestService {
    @Autowired
    private TransactionRequestRepository transactionRequestRepository;
    @Autowired
    private IUserRepository userRepository;
    @Autowired
    private FactureRepository factureRepository;
    @Autowired
    private NotificationService notificationService;

    public Transaction createTransactionRequest(TransactionRequest transactionRequest, Authentication authentication) {
        String clientEmail = authentication.getName();
        User client = userRepository.findByEmail(clientEmail)
                .orElseThrow(() -> new UsernameNotFoundException("Client not found"));
        User fournisseur = userRepository.findByEmail(transactionRequest.getClientEmail())
                .orElseThrow(() -> new UsernameNotFoundException("Fournisseur not found"));
        Facture facture = factureRepository.findById(transactionRequest.getFactureID())
                .orElseThrow(() -> new UsernameNotFoundException("Facture not found"));

        Transaction transaction = Transaction.builder()
                .amount(transactionRequest.getAmount())
                .client(client)
                .supplier(fournisseur)
                .facture(facture)
                .requestDate(LocalDateTime.now())
                .status(TransactionStatus.PENDING)
                .title(transactionRequest.getTitle())
                .description(transactionRequest.getDescription())
                .build();
        transactionRequestRepository.save(transaction);

        // Envoyer une notification au client
        String notificationMessage = String.format("Nouvelle demande de virement reçue, ID: %d", transaction.getId());
        notificationService.sendNotification("/topic/notifications/" + client.getEmail(), notificationMessage);

        return transaction;
    }

    public Transaction updateTransactionStatus(Long transactionId,TransactionStatus newStatus){
        Transaction transaction = transactionRequestRepository.findById(transactionId)
                .orElseThrow(() -> new UsernameNotFoundException("Transaction not found"));
        transaction.setStatus(newStatus);
        transactionRequestRepository.save(transaction);
        return transaction;
    }

    public List<Map<String, Object>> getAllTransactionRequests(User client) {
        List<Transaction> transactions = transactionRequestRepository.findByClient(client);
        return transactions.stream().map(t -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", t.getId());
            map.put("amount", t.getAmount());
            map.put("client_name", t.getClient() != null ? t.getClient().getFirstname() : null);
            map.put("supplier_id", t.getSupplier() != null ? t.getSupplier().getId() : null);
            map.put("supplier_name", t.getSupplier() != null ? t.getSupplier().getFirstname(): null);
            map.put("requestDate", t.getRequestDate());
            map.put("status",t.getStatus());
            map.put("title",t.getTitle());
            map.put("description",t.getDescription());

            if (t.getFacture() != null) {
                Map<String, Object> factureMap = new HashMap<>();
                factureMap.put("id", t.getFacture().getId());
                factureMap.put("fileName", t.getFacture().getFileName());
                factureMap.put("uploadTime", t.getFacture().getUploadTime());
                factureMap.put("amount", t.getFacture().getAmount());
                factureMap.put("filePath", t.getFacture().getFilePath());
                map.put("facture", factureMap);
            }
            return map;
        }).collect(Collectors.toList());
    }

    public List<Map<String, Object>> getAllTransactionToBenef(User supplier) {
        List<Transaction> transactions = transactionRequestRepository.findBysupplier(supplier);
        return transactions.stream().map(t -> {
            Map<String, Object> map = new HashMap<>();
            map.put("id", t.getId());
            map.put("amount", t.getAmount());
            map.put("client_name", t.getClient() != null ? t.getClient().getFirstname() : null);
            map.put("supplier_id", t.getSupplier() != null ? t.getSupplier().getId() : null);
            map.put("supplier_name", t.getSupplier() != null ? t.getSupplier().getFirstname(): null);
            map.put("supplier_email", t.getSupplier() != null ? t.getClient().getEmail() : null);
            map.put("requestDate", t.getRequestDate());
            map.put("status",t.getStatus());
            if (t.getFacture() != null) {
                Map<String, Object> factureMap = new HashMap<>();
                factureMap.put("id", t.getFacture().getId());
                factureMap.put("fileName", t.getFacture().getFileName());
                factureMap.put("uploadTime", t.getFacture().getUploadTime());
                factureMap.put("amount", t.getFacture().getAmount());
                factureMap.put("filePath", t.getFacture().getFilePath());
                map.put("facture", factureMap);
            }
            return map;
        }).collect(Collectors.toList());
    }

    public Optional<Transaction> getTransactionRequestById(Long id) {
        return transactionRequestRepository.findById(id);
    }

    public void deleteTransactionRequest(Long id) {
        transactionRequestRepository.deleteById(id);
    }
}