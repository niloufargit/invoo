package com.invoo.orchestrator.domaine.poubelle.Entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "_comptable")

public class Comptable {

    @Id
    @GeneratedValue
    private String codeAcces;
    private String lienAcces;
}
