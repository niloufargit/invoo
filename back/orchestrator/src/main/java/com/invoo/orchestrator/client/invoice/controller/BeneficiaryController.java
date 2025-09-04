package com.invoo.orchestrator.client.invoice.controller;

import com.invoo.global.beneficiary.BeneficiaryRequest;
import com.invoo.orchestrator.client.invoice.service.InvoiceServiceClient;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/beneficiaries")
@Slf4j
public class BeneficiaryController {

    private final InvoiceServiceClient invoiceServiceClient;

    public BeneficiaryController(InvoiceServiceClient invoiceServiceClient) {
        this.invoiceServiceClient = invoiceServiceClient;
    }


    @PostMapping("/add")
    public ResponseEntity<?> addBeneficiary(
            @Valid @RequestBody BeneficiaryRequest request,
            Authentication authentication
    ) {
        log.info("Adding beneficiary: {}", request);
        return invoiceServiceClient.addBeneficiary(request);
    }

    @GetMapping("/{idCompany}")
    public ResponseEntity<?> findBeneficiaryByIdCompany( @PathVariable Long idCompany ,Authentication authentication) {
        log.info("Retrieving beneficiaries for company id: {}", idCompany);
        var response =  invoiceServiceClient.getCompaniesBeneficiaryByCompany(idCompany);
        return ResponseEntity.ok( response );
    }


    @DeleteMapping("/{idCompany}/{idBeneficiary}")
    public ResponseEntity<?> deleteBeneficiaryById(
            @PathVariable Long idCompany,
            @PathVariable Long idBeneficiary
    ) {
        return invoiceServiceClient.deleteBeneficiaryById(idCompany, idBeneficiary);
    }

}