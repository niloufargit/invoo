package com.invoo.orchestrator.domaine.poubelle.Entity.transactionRequest;


import com.invoo.orchestrator.domaine.poubelle.Entity.facture.Facture;
import com.invoo.orchestrator.domaine.entity.User;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_transaction")
public class Transaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "client_id", nullable = false)
    private User client;

    @ManyToOne
    @JoinColumn(name = "supplier_id", nullable = false)
    private User supplier;

    private Double amount;

    @OneToOne
    @JoinColumn(name = "facture_id")
    private Facture facture;

    private LocalDateTime requestDate;
    @Enumerated(EnumType.STRING)
    private TransactionStatus status;
    private String title;
    private String description;

}
