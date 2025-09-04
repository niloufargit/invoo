package com.invoo.invoice.company;

import com.invoo.invoice.address.Address;
import com.invoo.invoice.address.AddressRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
@Slf4j
public class CompanyStorage implements ICompanyStorage {

    private final CompanyRepository companyRepository;
    private final AddressRepository addressRepository;

    public CompanyStorage(CompanyRepository companyRepository, AddressRepository addressRepository) {
        this.companyRepository = companyRepository;
        this.addressRepository = addressRepository;
    }

    @Override
    public Company save(Company company) {
        Address savedAddress = addressRepository.save(company.getAddress());
        company.setAddress(savedAddress);
        return companyRepository.save(company);
    }

    @Override
    public Company update(Company company, Long companyId) {

        log.info( "Mapped Company : {}", company );
        var existingCompany = companyRepository.findById(String.valueOf(companyId));

        if (existingCompany.isEmpty()) {
            throw new RuntimeException("Company not found with id: " + companyId);
        }
        // Update the address if it has changed

        Company c = new Company();
        c.setId(existingCompany.get().getId());
        c.setUserId(existingCompany.get().getUserId());
        c.setAddress(company.getAddress());
        c.getAddress().setId( existingCompany.get().getAddress().getId() );
        c.setName(company.getName());
        c.setSirenNumber(company.getSirenNumber());
        c.setPhoneNumber(company.getPhoneNumber());
        c.setEmail(company.getEmail());
        c.setCompanyType(company.getCompanyType());
        // Save the updated company
        log.info( "Prepared Company to save: {}", c );
        addressRepository.save( c.getAddress() );
        return companyRepository.save( c );
    }

    @Override
    public List<Company> getCompaniesByUserId(UUID userId) {
        return companyRepository.getCompaniesByUserId(userId);
    }

    @Override
    public Company findById(String companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new RuntimeException("Company not found with id: " + companyId));
    }

    @Override
    public List<BeneficiaryCompanyDto> searchCompanies(UUID userId, String name, String sirenNumber) {
        return companyRepository.searchCompanies(userId, name.toUpperCase(), sirenNumber);
    }

    @Override
    public List<Company> getCompaniesByIds(List<Long> ids) {
        return companyRepository.getCompaniesByIds(ids);
    }
}