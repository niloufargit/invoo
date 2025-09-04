package com.invoo.orchestrator.domaine.poubelle.Entity.facture;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class FactureRequest {
    private String amount;
}
