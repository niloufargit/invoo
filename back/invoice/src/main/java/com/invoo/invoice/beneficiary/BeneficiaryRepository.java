package com.invoo.invoice.beneficiary;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeneficiaryRepository extends JpaRepository<Beneficiary, UUID> {


    @Query("""
            SELECT CASE
            WHEN COUNT(b) > 0 THEN true
            ELSE false
            END
            FROM Beneficiary b
            WHERE b.idCustomerBeneficiary = :id
            AND b.idSupplierCompany = :idSelectedCompany
""")
    boolean checkIfNotYetBeneficiary(UUID id,
                                     @NotBlank(message = "idSelectedCompany is mandatory")
                                                    @NotNull(message = "idSelectedCompany is mandatory")
                                                    Long idSelectedCompany);

    @Query("""
            SELECT b
            FROM Beneficiary b
            WHERE  b.idSupplierCompany = :idCompany
            """)
    List<Beneficiary> findAllByIdCompany(Long idCompany);

    @Query("""
            SELECT CASE
            WHEN COUNT(b) > 0 THEN true
            ELSE false
            END
            FROM Beneficiary b
            WHERE b.idSupplierCompany = :idSelectedCompany
            AND b.idCompanyBeneficiary = :idCompany
            """)
    boolean checkIfCompanyBeneficiaryExists(Long idCompany, Long idSelectedCompany);



    Optional<Beneficiary> findByIdCompanyBeneficiaryAndIdSupplierCompany(Long idCompany, Long idBeneficiary);
}

