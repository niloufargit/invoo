package com.invoo.invoice.company;

import com.invoo.invoice.address.Address;

import java.util.UUID;


public record CompanyRequest(
        Long id,
        String name,
        String sirenNumber,
        String numberStreet,
        String street,
        String city,
        String zipCode,
        String country,
        String phoneNumber,
        String email,
        String logo,
        CompanyType companyType
) {
    public static Company toCompany(CompanyRequest companyRequest, UUID userId) {
        return new Company(
                companyRequest.id(),
                companyRequest.name(),
                companyRequest.sirenNumber(),
                new Address(
                        companyRequest.id(),
                        companyRequest.numberStreet(),
                        companyRequest.street(),
                        companyRequest.city(),
                        companyRequest.zipCode(),
                        companyRequest.country()
                ),
                companyRequest.phoneNumber(),
                companyRequest.email(),
                userId,
                companyRequest.companyType()
        );
    }
}
