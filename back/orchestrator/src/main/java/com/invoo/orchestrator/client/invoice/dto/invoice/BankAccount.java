package com.invoo.orchestrator.client.invoice.dto.invoice;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
public class BankAccount {
    private String iban;
    private String bic;
    private String bankName;
}
