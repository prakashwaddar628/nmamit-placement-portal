package com.nmamit.placement_backend.Application.service;

import com.nmamit.placement_backend.Application.dto.request.ApplicationRequest;
import com.nmamit.placement_backend.Application.dto.response.ApplicationResponse;
import com.nmamit.placement_backend.enums.ApplicationStatus;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ApplicationService {
    
    ApplicationResponse applyForJob (
        String collegeEmail,
        ApplicationRequest request
    );

    List<ApplicationResponse> getMyApplications(
        String collegeEmail
    );

    List<ApplicationResponse> getAllApplications();

    ApplicationResponse updateApplicationStatus(
        Long applicationId,
        ApplicationStatus status
    );

}
