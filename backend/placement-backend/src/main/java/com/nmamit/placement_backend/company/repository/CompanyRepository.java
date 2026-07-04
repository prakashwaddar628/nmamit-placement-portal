package com.nmamit.placement_backend.company.repository;

import com.nmamit.placement_backend.company.entity.Company;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CompanyRepository extends JpaRepository<Company, Long> {
    
    boolean existsByCompanyNameIgnoreCase(String companyName);
    
}
