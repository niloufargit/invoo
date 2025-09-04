package com.invoo.payment.payment.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "stripe_records")
@AllArgsConstructor
@NoArgsConstructor
public class StripeRecord {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private UUID paymentId;
    private String status;
    private String message;
    private String sessionId;
    @Column(length = 1000)
    private String urlSession;

}
