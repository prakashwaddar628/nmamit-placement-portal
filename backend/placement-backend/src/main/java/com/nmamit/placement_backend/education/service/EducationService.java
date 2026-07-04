package com.nmamit.placement_backend.education.service;

import com.nmamit.placement_backend.education.dto.request.EducationRequest;
import com.nmamit.placement_backend.education.dto.response.EducationResponse;

import java.util.List;

public interface EducationService {
    
    EducationResponse addEducation(
        String collegeEmail, 
        EducationRequest request
    );

    List<EducationResponse> getEducation(String collegeEmail);

    EducationResponse updateEducation(
        String collegeEmail, 
        Long educationId, 
        EducationRequest request
    );

    void deleteEducation(
        String collegeEmail, 
        Long educationId
    );

}
