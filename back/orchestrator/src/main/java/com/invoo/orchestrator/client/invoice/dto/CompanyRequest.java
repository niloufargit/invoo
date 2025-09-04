package com.invoo.orchestrator.client.invoice.dto;

import com.invoo.global.invoice.CompanyType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record CompanyRequest (
        String id,

        @NotNull(message = "Company name is required. Should not be null")
        @NotBlank(message = "Company name is required. Should not be blank")
        String name,

        @NotNull(message = "Company SIREN number is required. Should not be null")
        @NotBlank(message = "Company SIREN number is required. Should not be blank")
        String sirenNumber,

        @NotNull(message = "Company phone number is required. Should not be null")
        @NotBlank(message = "Company phone number is required. Should not be blank")
        String phoneNumber,

        @NotNull(message =  "Company numberStreet is required. Should not be null")
        @NotBlank(message = "Company numberStreet is required. Should not be blank")
        String numberStreet,

        @NotNull(message =  "Company street is required. Should not be null")
        @NotBlank(message = "Company street is required. Should not be blank")
        String street,

        @NotNull(message =  "Company city is required. Should not be null")
        @NotBlank(message = "Company city is required. Should not be blank")
        String city,

        @NotNull(message =  "Company zipCode is required. Should not be null")
        @NotBlank(message = "Company zipCode is required. Should not be blank")
        String zipCode,

        String country,

        @NotNull(message = "Company email is required. Should not be null")
        @NotBlank(message = "Company email is required. Should not be blank")
        String email,

        @NotNull(message = "Company type is required. Should not be null") CompanyType companyType,

        String logo

) {
}
