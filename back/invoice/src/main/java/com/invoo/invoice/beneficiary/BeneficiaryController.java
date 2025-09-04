package com.invoo.invoice.beneficiary;

import com.invoo.global.beneficiary.BeneficiaryRequest;
import com.invoo.invoice.company.Company;
import com.invoo.invoice.company.CompanyService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/invoices/beneficiaries")
@Slf4j
public class BeneficiaryController {

    private final BeneficiaryService beneficiaryService;
    private final CompanyService companyService;

    public BeneficiaryController(BeneficiaryService beneficiaryService, CompanyService companyService) {
        this.beneficiaryService = beneficiaryService;
        this.companyService = companyService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> add(@RequestBody BeneficiaryRequest request) {
        log.info("Adding beneficiary: {}", request);
        return ResponseEntity.ok().body( beneficiaryService.addBeneficiary( request ) );
    }


    @GetMapping("/getByIdCompany/{ids}")
    public ResponseEntity<?> retrieveBeneficiariesByIdCompany(@PathVariable Long ids) {
        log.info("Retrieving beneficiaries for company id: {}", ids);
        List<Company> companies = beneficiaryService.retrieveBeneficiariesByIdCompany(ids);
        return ResponseEntity.ok(companies);
    }

    @DeleteMapping("/{idCompany}/{idBeneficiary}")
    public ResponseEntity<?> deleteBeneficiary(
            @PathVariable Long idCompany,
            @PathVariable Long idBeneficiary
    ) {
        log.info("delete beneficiaries for company id: {}", idCompany);
        return beneficiaryService.deleteBeneficiary(idCompany, idBeneficiary);
    }

}
