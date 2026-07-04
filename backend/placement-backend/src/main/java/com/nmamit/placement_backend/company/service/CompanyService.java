package com.nmamit.placement_backend.company.service;

import com.nmamit.placement_backend.company.dto.request.CompanyRequest;
import com.nmamit.placement_backend.company.dto.response.CompanyResponse;

import java.util.List;

public interface CompanyService {
    
    CompanyResponse createCompany(CompanyRequest request);

    List<CompanyResponse> getAllCompanies();

    CompanyResponse getCompanyById(Long id);

    CompanyResponse updateCompany(Long id, CompanyRequest request);

    void deleteCompany(Long id);
}
