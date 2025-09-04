package com.invoo.invoice.company;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/invoices/companies")
@Slf4j
public class CompanyController {

    private final CompanyService companyService;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/{userId}")
    public ResponseEntity<?> createCompany(@RequestBody CompanyRequest request, @PathVariable UUID userId) {
        return ResponseEntity.ok().body( companyService.createCompany(request, userId) );
    }

    @PostMapping("/update/{companyId}")
    public ResponseEntity<?> updateCompany(
            @RequestBody CompanyRequest request,
            @PathVariable Long companyId) {
        log.info( "Received Company : {}", request );
        log.info( "Company ID : {}", companyId );
        return ResponseEntity.ok().body( companyService.updateCompany(request, companyId) );
    }

    @GetMapping("/{userId}")
    public ResponseEntity<?> getCompanies(@PathVariable UUID userId) {
        List<Company> companies = companyService.getCompanies(userId);
        return ResponseEntity.ok(companies);
    }

    @GetMapping("/get/{companyId}")
    public ResponseEntity<?> getCompany(@PathVariable Long companyId) {
        companyService.getCompanyById(companyId);
        return ResponseEntity.ok().build();
    }


    @PostMapping("/search")
    public ResponseEntity<?> searchCompanies(@RequestParam String name,
                                             @RequestParam String sirenNumber,
                                             @RequestParam UUID userId) {
        return companyService.searchCompanies(userId,name, sirenNumber);
    }

    @PostMapping("/getByIds")
    public ResponseEntity<?> getCompaniesByIds(@RequestBody List<Long> ids) {
        List<Company> companies = companyService.getCompaniesByIds(ids);
        return ResponseEntity.ok(companies);
    }

}
