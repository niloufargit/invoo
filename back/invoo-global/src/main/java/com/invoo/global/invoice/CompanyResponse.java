package com.invoo.global.invoice;

public record CompanyResponse(
        String id,
        String name,
        String sirenNumber,
        String phoneNumber,
        Address address,
        String email,
        String logo,
        CompanyType companyType
) {
}
