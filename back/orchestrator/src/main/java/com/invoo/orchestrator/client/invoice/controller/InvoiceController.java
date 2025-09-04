package com.invoo.orchestrator.client.invoice.controller;

import com.invoo.orchestrator.application.exceptions.ApplicationExceptions;
import com.invoo.orchestrator.client.invoice.dto.CompanyRequest;
import com.invoo.global.invoice.CompanyResponse;
import com.invoo.orchestrator.client.invoice.dto.invoice.InvoiceRequest;
import com.invoo.orchestrator.client.invoice.service.InvoiceServiceClient;
import com.invoo.orchestrator.client.invoice.service.UtilsService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

@RestController
@Slf4j
@RequestMapping("/api/v1/invoices")
public class InvoiceController {
    // Invoice related API endpoints

    private final InvoiceServiceClient invoiceService;
    private final UtilsService utilsService;

    public InvoiceController(InvoiceServiceClient invoiceService, UtilsService utilsService) {
        this.invoiceService = invoiceService;
        this.utilsService = utilsService;
    }

    // API endpoints

    // Create Company API
    @PostMapping("/companies")
    public ResponseEntity<?> createCompany(@Valid @RequestBody CompanyRequest request, Principal principal) {

        if (Objects.isNull(principal))
            ApplicationExceptions.userNotConnected();

        var userId = getUserId(principal);
        return invoiceService.createCompany(request, userId);
    }

    @PutMapping("/companies/update/{companyId}")
    public ResponseEntity<?> updateCompany(
            @Valid @RequestBody CompanyRequest request, Principal principal,
            @PathVariable String companyId) {

        if (Objects.isNull(principal))
            ApplicationExceptions.userNotConnected();

        return invoiceService.updateCompany(request, companyId);
    }

    @GetMapping("/companies")
    public ResponseEntity<?> getCompanies(Principal principal) {

        if (Objects.isNull(principal))
            ApplicationExceptions.userNotConnected();

        var userId = getUserId(principal);
        return invoiceService.getCompanies(userId);
    }

    @PostMapping("/companies/search")
    public ResponseEntity<?> searchCompanies(@RequestParam String name,
                                             @RequestParam String sirenNumber,
                                             Principal principal
    ) {

        var userId = getUserId(principal);
        return invoiceService.searchCompanies(name, sirenNumber, userId);
    }

    @GetMapping("/getByIds")
    public ResponseEntity<?> getCompaniesByIds(@RequestBody List<Long> ids) {
        List<CompanyResponse> companies = invoiceService.getCompaniesByIds(ids);
        return ResponseEntity.ok(companies);
    }

    private UUID getUserId(Principal principal) {
        return utilsService.getUserId(principal.getName());
    }

    @PostMapping("/create")
    public ResponseEntity<?> createInvoice(@Valid @RequestBody InvoiceRequest request) {
        return invoiceService.createInvoice(request);
    }

    @GetMapping("/record/{companyId}")
    public ResponseEntity<?> getCompanyInvoices(@PathVariable Long companyId) {
        log.info("Retrieving invoice for company id: {}", companyId);
        return invoiceService.getInvoiceByCompanyId(companyId);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getInvoiceById(@PathVariable Long id) {
        log.info("Retrieving invoice with id: {}", id);
        return invoiceService.getInvoiceById(id);
    }

    @GetMapping("/statistics/products/{companyId}")
    public ResponseEntity<?> getProductsStatistics(@PathVariable Long companyId) {
        log.info("Retrieving products statistics");
        return invoiceService.getProductsStatistics(companyId);
    }

    @GetMapping("/download/{invoiceId}")
    public ResponseEntity<?> downloadInvoicePdf(@PathVariable Long invoiceId) {
        log.info("Downloading invoice PDF with id: {}", invoiceId);
        return invoiceService.downloadInvoicePdf(invoiceId);
    }

}
