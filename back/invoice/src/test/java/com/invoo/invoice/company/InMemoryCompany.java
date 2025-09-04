package com.invoo.invoice.company;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class InMemoryCompany implements ICompanyStorage {
    private final List<Company> companies = new ArrayList<>();

    @Override
    public Company save(Company company) {
        companies.add(company);
        return company;
    }

    @Override
    public Company update(Company company, Long companyId) {
        return null;
    }

    @Override
    public List<Company> getCompaniesByUserId(UUID userId) {
        return companies.stream()
                .filter(company -> company.getUserId().equals(userId))
                .toList();
    }

    @Override
    public Company findById(String companyId) {
        return companies.stream()
                .filter(company -> company.getId().toString().equals(companyId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));
    }

    @Override
    public List<BeneficiaryCompanyDto> searchCompanies(UUID userId, String name, String sirenNumber) {
        return List.of();
    }

    @Override
    public List<Company> getCompaniesByIds(List<Long> ids) {
        return List.of();
    }
}