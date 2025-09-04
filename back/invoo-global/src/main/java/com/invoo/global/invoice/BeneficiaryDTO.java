package com.invoo.global.invoice;

import java.util.UUID;

public record BeneficiaryDTO(
        UUID id,
        UUID idCustomerBeneficiary,
        Long idCompanyBeneficiary,
        Long idSupplierCompany
) {


}
