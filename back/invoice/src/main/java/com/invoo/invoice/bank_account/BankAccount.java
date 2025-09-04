package com.invoo.invoice.bank_account;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Entity
public class BankAccount {
    @Id
    private Long id;
    private String iban;
    private String bic;
    private String bankName;
}
