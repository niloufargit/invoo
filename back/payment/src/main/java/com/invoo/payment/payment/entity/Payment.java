package com.invoo.payment.payment.entity;

import com.invoo.global.payment.dto.RecipientType;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "payments")
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    private Long companyId;

    private Long senderId;
    private String recipientId;
    @Enumerated(EnumType.STRING)
    private RecipientType recipientType;
    private String recipientName;
    private String recipientEmail;
    @Enumerated(EnumType.STRING)
    private Status status;
    private Long invoiceId;
    private String invoiceName;
    private Double amount;
    private LocalDateTime createdAt;


}
