package com.invoo.invoice.company;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class CompanyService {

    private final ICompanyStorage companyStorage;

    public CompanyService(ICompanyStorage companyStorage) {
        this.companyStorage = companyStorage;
    }

    public Company createCompany(CompanyRequest request, UUID userId) {
        var company = CompanyRequest.toCompany(request, userId);
        return companyStorage.save(company);
    }

    public List<Company> getCompanies(UUID userId) {
        return companyStorage.getCompaniesByUserId(userId);
    }

    public Company getCompanyById(Long companyId) {
        return companyStorage.findById(companyId.toString());
    }

    public ResponseEntity<?> searchCompanies(UUID userId, String name, String sirenNumber) {
        List<BeneficiaryCompanyDto> companies = companyStorage.searchCompanies(userId, name, sirenNumber);
        return ResponseEntity.ok(companies);
    }

    public List<Company> getCompaniesByIds(List<Long> ids) {
        return companyStorage.getCompaniesByIds(ids);
    }

    public Object updateCompany(CompanyRequest request, Long companyId) {
        return companyStorage.update(
                CompanyRequest.toCompany(request, UUID.randomUUID()), companyId);
    }
}