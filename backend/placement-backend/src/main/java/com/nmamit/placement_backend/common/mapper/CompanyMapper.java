package com.nmamit.placement_backend.common.mapper;

import com.nmamit.placement_backend.company.dto.response.CompanyResponse;
import com.nmamit.placement_backend.company.entity.Company;
import org.springframework.stereotype.Component;

@Component
public class CompanyMapper {

    public CompanyResponse toResponse(Company company) {
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
}
