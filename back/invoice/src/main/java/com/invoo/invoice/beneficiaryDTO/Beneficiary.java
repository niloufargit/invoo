package com.invoo.invoice.beneficiaryDTO;

import com.invoo.invoice.address.Address;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class Beneficiary {
    private String id;
    private String firstName;
    private String lastName;
    private String name;
    private String email;
    private Address address;
    private String beneficiaryVATNumber;
}
