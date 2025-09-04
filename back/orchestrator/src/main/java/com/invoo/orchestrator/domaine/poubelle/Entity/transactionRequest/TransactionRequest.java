package com.invoo.orchestrator.domaine.poubelle.Entity.transactionRequest;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class TransactionRequest {
    private String ClientEmail;
    private double amount;
    private Long factureID;
    private String title;
    private String description;
}
