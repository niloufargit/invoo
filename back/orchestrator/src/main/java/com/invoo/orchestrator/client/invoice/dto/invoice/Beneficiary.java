package com.invoo.orchestrator.client.invoice.dto.invoice;

import com.invoo.global.invoice.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Beneficiary {
    private Long id;
    private String firstname;
    private String lastname;
    private String name;
    private String email;
    private Address address;
    private String customerVATNumber;

}
