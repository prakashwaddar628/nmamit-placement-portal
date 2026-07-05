package com.nmamit.placement_backend.company.service.impl;

import com.nmamit.placement_backend.common.exception.BadRequestException;
import com.nmamit.placement_backend.common.exception.ResourceNotFoundException;
import com.nmamit.placement_backend.common.mapper.CompanyMapper;
import com.nmamit.placement_backend.company.dto.request.CompanyRequest;
import com.nmamit.placement_backend.company.dto.response.CompanyResponse;
import com.nmamit.placement_backend.company.entity.Company;
import com.nmamit.placement_backend.company.repository.CompanyRepository;
import com.nmamit.placement_backend.company.service.CompanyService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class CompanyServiceImpl implements CompanyService {

    private final CompanyRepository companyRepository;
    private final CompanyMapper companyMapper;

    private Company getCompany(Long id) {
        return companyRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Company not found"));
    }

    @Override
    public CompanyResponse createCompany(CompanyRequest request) {
        log.info("Creating company: {}", request.getCompanyName());
        if (companyRepository.existsByCompanyNameIgnoreCase(request.getCompanyName())) {
            throw new BadRequestException("Company already exists.");
        }
        Company company = Company.builder()
                .companyName(request.getCompanyName())
                .website(request.getWebsite())
                .industry(request.getIndustry())
                .description(request.getDescription())
                .logoUrl(request.getLogoUrl())
                .active(request.getActive() == null ? Boolean.TRUE : request.getActive())
                .build();
        Company saved = companyRepository.save(company);
        log.info("Company created with id: {}", saved.getId());
        return companyMapper.toResponse(saved);
    }

    @Override
    public List<CompanyResponse> getAllCompanies() {
        log.debug("Fetching all active companies");
        return companyRepository.findByActiveTrue()
                .stream()
                .map(companyMapper::toResponse)
                .toList();
    }

    @Override
    public CompanyResponse getCompanyById(Long id) {
        return companyMapper.toResponse(getCompany(id));
    }

    @Override
    public CompanyResponse updateCompany(Long id, CompanyRequest request) {
        log.info("Updating company id: {}", id);
        Company company = getCompany(id);
        if (!company.getCompanyName().equalsIgnoreCase(request.getCompanyName())
                && companyRepository.existsByCompanyNameIgnoreCase(request.getCompanyName())) {
            throw new BadRequestException("Company already exists.");
        }
        company.setCompanyName(request.getCompanyName());
        company.setWebsite(request.getWebsite());
        company.setIndustry(request.getIndustry());
        company.setDescription(request.getDescription());
        company.setLogoUrl(request.getLogoUrl());
        company.setActive(request.getActive());
        return companyMapper.toResponse(companyRepository.save(company));
    }

    @Override
    public void deleteCompany(Long id) {
        log.info("Soft-deleting company id: {}", id);
        Company company = getCompany(id);
        company.setActive(false);
        companyRepository.save(company);
    }
}
