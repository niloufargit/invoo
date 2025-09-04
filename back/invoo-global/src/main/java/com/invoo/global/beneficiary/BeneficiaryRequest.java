package com.invoo.global.beneficiary;


import jakarta.validation.constraints.NotNull;

public record BeneficiaryRequest(
        String email,
        Long idCompany,

        @NotNull(message = "idSelectedCompany is mandatory")
        Long idSelectedCompany
) {

}