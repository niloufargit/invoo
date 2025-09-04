package com.invoo.orchestrator.domaine.entity.dto.address;

public record AddressDTO(
        Long id,
        String streetNumber,
        String street,
        String city,
        String zipCode,
        String country
) {

}
