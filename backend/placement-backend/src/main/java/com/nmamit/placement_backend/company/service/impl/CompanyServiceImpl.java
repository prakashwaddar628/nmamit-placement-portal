package com.nmamit.placement_backend.company.service.impl;

import com.nmamit.placement_backend.common.exception.BadRequestException;
import com.nmamit.placement_backend.common.exception.ResourceNotFoundException;
import com.nmamit.placement_backend.company.dto.request.CompanyRequest;
import com.nmamit.placement_backend.company.dto.response.CompanyResponse;
import com.nmamit.placement_backend.company.entity.Company;
import com.nmamit.placement_backend.company.repository.CompanyRepository;
import com.nmamit.placement_backend.company.service.CompanyService;

import lombok.RequiredArgsConstructor;

import java.util.List;

import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;

    private Company getCompany(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }

    private CompanyResponse mapToResponse(Company company) {
        return CompanyResponse.builder()
                .id(company.getId())
                .companyName(company.getCompanyName())
                .website(company.getWebsite())
                .industry(company.getIndustry())
                .description(company.getDescription())
                .logoUrl(company.getLogoUrl())
                .active(company.getActive())
                .build();
    }

    @Override
    public CompanyResponse createCompany(
            CompanyRequest request) {

        if (companyRepository.existsByCompanyNameIgnoreCase(request.getCompanyName())) {
            throw new BadRequestException("Company already exists.");
        }

        Company company = Company.builder()
                .companyName(request.getCompanyName())
                .website(request.getWebsite())
                .industry(request.getIndustry())
                .description(request.getDescription())
                .logoUrl(request.getLogoUrl())
                .active(request.getActive() == null ? true : request.getActive())
                .build();

        Company saved = companyRepository.save(company);

        return mapToResponse(saved);
    }

    @Override
    public List<CompanyResponse> getAllCompanies() {
        return companyRepository.findByActiveTrue()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        return mapToResponse(getCompany(id));
    }

    @Override
    public CompanyResponse updateCompany(
            Long id,
            CompanyRequest request) {

        Company company = getCompany(id);

        if (!company.getCompanyName().equalsIgnoreCase(request.getCompanyName())
            && companyRepository.existsByCompanyNameIgnoreCase(request.getCompanyName())){

                throw new BadRequestException("Company already exists.");
        }

        company.setCompanyName(request.getCompanyName());
        company.setWebsite(request.getWebsite());
        company.setIndustry(request.getIndustry());
        company.setDescription(request.getDescription());
        company.setLogoUrl(request.getLogoUrl());
        company.setActive(request.getActive());

        Company updated = companyRepository.save(company);

        return mapToResponse(updated);
    }

    @Override
    public void deleteCompany(Long id) {

        Company company = getCompany(id);
        company.setActive(false);
        companyRepository.save(company);
    }

}
