package com.invoo.orchestrator.domaine.poubelle.Entity.paiement;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder

public class PaiementRequest {
    private int montant;
    private String fournisseurEmail;
}
