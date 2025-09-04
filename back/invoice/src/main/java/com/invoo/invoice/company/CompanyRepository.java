package com.invoo.invoice.company;

import com.invoo.invoice.address.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.UUID;

public interface CompanyRepository extends JpaRepository<Company, String> {

    List<Company> getCompaniesByUserId(UUID userId);

    @Query("""
    SELECT new com.invoo.invoice.company.BeneficiaryCompanyDto(c.id, c.name, c.sirenNumber)
    FROM Company c
    WHERE (UPPER(c.name) LIKE %:name% OR c.sirenNumber = :sirenNumber) AND c.userId != :userId
""")
    List<BeneficiaryCompanyDto> searchCompanies(UUID userId, String name, String sirenNumber);

    @Query("SELECT c FROM Company c WHERE c.id IN :ids")
    List<Company> getCompaniesByIds(List<Long> ids);

    @Modifying
    @Query("""
        UPDATE Company c
                SET c.name = :name,
                        c.sirenNumber = :sirenNumber,
                                c.address = :address,
                                        c.email = :email,
                                c.phoneNumber = :phoneNumber,
                                        c.companyType = :companyType
                                        WHERE c.id = :companyId""")
    void updateCompany(   String name,
                             String sirenNumber,
                             Address address,
                             String phoneNumber,
                             String email,
                             CompanyType companyType,
                             String companyId);



}
