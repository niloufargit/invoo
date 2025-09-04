package com.invoo.invoice.company;

import java.util.List;
import java.util.UUID;

public interface ICompanyStorage {
    Company save(Company company);
    Company update(Company company, Long companyId);

    List<Company> getCompaniesByUserId(UUID userId);
    Company  findById(String companyId);

    List<BeneficiaryCompanyDto> searchCompanies(UUID userId, String name, String sirenNumber);

    List<Company> getCompaniesByIds(List<Long> ids);
}