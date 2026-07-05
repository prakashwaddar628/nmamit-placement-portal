package com.nmamit.placement_backend.common.mapper;

import com.nmamit.placement_backend.Application.dto.response.ApplicationResponse;
import com.nmamit.placement_backend.Application.entity.Application;
import org.springframework.stereotype.Component;

@Component
public class ApplicationMapper {

    public ApplicationResponse toResponse(Application application) {
        return ApplicationResponse.builder()
                .id(application.getId())
                .companyName(application.getJobDrive().getCompany().getCompanyName())
                .jobRole(application.getJobDrive().getJobRole())
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .build();
    }
}
