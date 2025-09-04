package com.invoo.orchestrator.client.invoice.service;

import com.invoo.global.beneficiary.BeneficiaryCompanyDto;
import com.invoo.global.beneficiary.BeneficiaryRequest;
import com.invoo.global.invoice.BeneficiaryDTO;
import com.invoo.global.invoice.CompanyResponse;
import com.invoo.global.invoice.Invoice;
import com.invoo.global.invoice.InvoiceRecord;
import com.invoo.global.statistic.ProductStatisticDTO;
import com.invoo.orchestrator.application.exceptions.ApplicationExceptions;
import com.invoo.orchestrator.client.invoice.dto.CompanyRequest;
import com.invoo.orchestrator.client.invoice.dto.invoice.InvoiceRequest;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.reactive.function.client.WebClientRequestException;

import java.util.List;
import java.util.Map;
import java.util.UUID;


public class InvoiceServiceClient {

    private static final Logger log = LoggerFactory.getLogger( InvoiceServiceClient.class);
    private final WebClient client;

    public InvoiceServiceClient(WebClient client) {
        this.client = client;
    }

    public ResponseEntity<?> createCompany(CompanyRequest request, UUID userId) {
        var response =  client.post()
                        .uri("/invoices/companies/{userId}", userId)
                        .bodyValue(request)
                        .retrieve()
                        .bodyToMono( CompanyResponse.class)
                        .block();
        log.info( "response for create company : {}",  response);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> updateCompany(@Valid CompanyRequest request, String companyId) {
        var response =  client.post()
                .uri("/invoices/companies/update/{companyId}", companyId)
                .bodyValue(request)
                .retrieve()
                .bodyToMono( CompanyResponse.class)
                .block();
        log.info( "response for Updated company : {}",  response);
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getCompanies(UUID userId) {
        log.info("Retrieving companies");
        var response = client.get()
                        .uri("/invoices/companies/{userId}", userId)
                        .retrieve()
                        .bodyToFlux(CompanyResponse.class)
                        .collectList()
                        .block();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getCompany(Long companyId) {
        log.info("Retrieving company");
        var response = client.get()
                .uri("/invoices/companies/get/{companyId}", companyId)
                .retrieve()
                .bodyToFlux(CompanyResponse.class)
                .collectList()
                .block();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> createInvoice(InvoiceRequest request) {
        var response = client.post()
                .uri("/invoices/create")
                .bodyValue(request)
                .retrieve()
                .bodyToMono(InvoiceRecord.class)
                .block();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> getInvoiceById(Long id) {

        var response = client.get()
                .uri("/invoices/{id}", id)
                .retrieve()
                .bodyToMono(Invoice.class)
                .block();
        return ResponseEntity.ok(response);
    }

    public ResponseEntity<?> searchCompanies(String name, String sirenNumber, UUID userId) {
        log.info("Searching companies");
        var response = client.post()
                        .uri("/invoices/companies/search?userId={userId}&name={name}&sirenNumber={sirenNumber}", userId, name, sirenNumber)
                        .retrieve()
                        .bodyToFlux( BeneficiaryCompanyDto.class)
                        .collectList()
                        .block();
        return ResponseEntity.ok(response);
    }

    public List<CompanyResponse> getCompaniesByIds(List<Long> companyIds) {
        return client.post()
                .uri("/invoices/companies/getByIds")
                .bodyValue(companyIds)
                .retrieve()
                .bodyToFlux( CompanyResponse.class)
                .collectList()
                .block();
    }


    public List<CompanyResponse> getCompaniesBeneficiaryByCompany(Long companyId) {
        log.info("Retrieving beneficiaries for company id: {}", companyId);
        log.info("Calling Invoice Service to get beneficiaries by company id: {}", companyId);
        return client.get()
                .uri("/invoices/beneficiaries/getByIdCompany/{ids}", companyId)
                .retrieve()
                .bodyToFlux( CompanyResponse.class)
                .collectList()
                .block();
    }

    public ResponseEntity<?> addBeneficiary(@Valid BeneficiaryRequest request) {
        log.info("Starting call Invoice Service to add beneficiary with request: {}", request);

        var resp = client.post()
                .uri("/invoices/beneficiaries/add")
                .bodyValue(request)
                .retrieve()
                .bodyToMono( BeneficiaryDTO.class)
                .log()
                .block();
        return ResponseEntity.ok(resp);
    }

    public ResponseEntity<?> getInvoiceByCompanyId(Long companyId) {
        log.info("Retrieving invoices for company id: {}", companyId);
        return client.get()
                .uri("/invoices/record/{companyId}", companyId)
                .retrieve()
                .bodyToFlux(InvoiceRecord.class)
                .collectList()
                .blockOptional()
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    public ResponseEntity<?> deleteBeneficiaryById(Long id, Long idBeneficiary) {

        log.info( "delete beneficiary by id : {}:{}", id, idBeneficiary );

        var response = this.client.delete()
                .uri("/invoices/beneficiaries/{id}/{idB}", id, idBeneficiary)
                .header( HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .retrieve()
                .onStatus(
                        HttpStatus.BAD_REQUEST::equals,
                        r -> r.bodyToMono(String.class).map( ApplicationExceptions::clientException ))
                .bodyToMono( String.class )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();

        return ResponseEntity.ok( Map.of("response", response) );
    }

    public ResponseEntity<?> getProductsStatistics(Long companyId) {
        var resp = client.get()
                .uri("/invoices/statistics/products/{companyId}", companyId)
                .retrieve()
                .bodyToFlux( ProductStatisticDTO.class)
                .collectList()
                .doOnSuccess( catalogs -> log.info("Product stat retrieved successfully!") )
                .onErrorResume( WebClientRequestException.class, e -> ApplicationExceptions.connectionException( "Catalog" ) )
                .block();

       return ResponseEntity.ok( resp );

    }

    public ResponseEntity<byte[]> downloadInvoicePdf(Long invoiceId) {
        log.info("Downloading PDF for invoice ID: {}", invoiceId);

        return client.get()
                .uri("/invoices/download/{invoiceId}", invoiceId)
                .accept(MediaType.APPLICATION_PDF)
                .retrieve()
                .toEntity(byte[].class)
                .block();
    }
}
