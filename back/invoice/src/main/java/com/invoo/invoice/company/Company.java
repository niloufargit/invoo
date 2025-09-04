package com.invoo.invoice.company;


import com.invoo.invoice.address.Address;
import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
@Entity
public class Company {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    private String sirenNumber;

    @ManyToOne
    @JoinColumn(name = "address_id")
    private Address address;

    private String phoneNumber;
    private String email;
    private UUID userId;
    @Enumerated(EnumType.STRING)
    private CompanyType companyType;
}
