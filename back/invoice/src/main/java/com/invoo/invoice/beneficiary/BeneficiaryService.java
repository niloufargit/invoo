package com.invoo.invoice.beneficiary;


import com.invoo.global.beneficiary.BeneficiaryRequest;
import com.invoo.invoice.company.Company;
import com.invoo.invoice.company.CompanyStorage;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * The type Beneficiary service.
 */
@Service
@AllArgsConstructor
@Slf4j
public class BeneficiaryService {


    private final BeneficiaryRepository beneficiaryRepository;
    private final CompanyStorage companyStorage;

    public Beneficiary addBeneficiary(BeneficiaryRequest request) {

        log.info("Adding beneficiary: {}", request);
        if (request.idCompany() != null && request.email() == null ) {
            return addProfetionnalBeneficiary(request.idCompany(), request.idSelectedCompany());
        }
        return null;
    }


    private Beneficiary addProfetionnalBeneficiary(Long idCompany,
                                                 @NotBlank(message = "idSelectedCompany is mandatory")
                                                 @NotNull(message = "idSelectedCompany is mandatory")
                                                   Long idSelectedCompany) {

        log.info("Adding professional beneficiary for company id: {} and selected company id: {}", idCompany, idSelectedCompany);
        if (checkIfCompanyBeneficiaryExists(idSelectedCompany, idCompany)) {
            return null;
        }

        Beneficiary beneficiary = Beneficiary.builder()
                .idCompanyBeneficiary( idCompany )
                .idSupplierCompany( idSelectedCompany )
                .build();
        log.info("Saving beneficiary: {}", beneficiary);
        return beneficiaryRepository.save(beneficiary);
    }

    private boolean checkIfCompanyBeneficiaryExists(@NotBlank(message = "idSelectedCompany is mandatory") @NotNull(message = "idSelectedCompany is mandatory") Long idSelectedCompany, Long idCompany) {
        if (beneficiaryRepository.checkIfCompanyBeneficiaryExists(idCompany, idSelectedCompany)) {
            log.warn("Beneficiary already exists for company with id: {} and selected company id: {}", idCompany, idSelectedCompany);
            return true;
        }
        log.info("No existing beneficiary found for company with id: {} and selected company id: {}", idCompany, idSelectedCompany);
        return false;
    }

    public List<Company> retrieveBeneficiariesByIdCompany(Long idCompany) {
        //String userEmail = authentication.getName();
        log.info("Retrieving beneficiaries for company id: {}", idCompany);
        var beneficiaries = beneficiaryRepository.findAllByIdCompany(idCompany);

        if (beneficiaries.isEmpty()) {
            log.info("No beneficiaries found for company with id: {}", idCompany);
            return  List.of();
        }

        List<Long> companyBeneficiaries = beneficiaries.stream()
                .map(Beneficiary::getIdCompanyBeneficiary)
                .toList();

        List<Company> companyResponses = companyStorage.getCompaniesByIds( companyBeneficiaries);

        log.info("Retrieved {} beneficiaries for company with id: {}", companyResponses.size(), idCompany);

        return  companyResponses;
    }


    public ResponseEntity<?> deleteBeneficiary(Long idCompany, Long idBeneficiary) {

        var b = beneficiaryRepository.findByIdCompanyBeneficiaryAndIdSupplierCompany(idCompany, idBeneficiary);

        beneficiaryRepository.deleteById( b.get().getId() );

       // beneficiaryRepository.deleteByIdCompanyBeneficiaryAndIdSupplierCompany(idCompany, idBeneficiary);
        log.info("Beneficiary with id: {} deleted successfully", idCompany);
        return ResponseEntity.ok().body("Beneficiary deleted successfully");
    }
}
