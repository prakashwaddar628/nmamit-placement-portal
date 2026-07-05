package com.nmamit.placement_backend.common.mapper;

import com.nmamit.placement_backend.education.dto.response.EducationResponse;
import com.nmamit.placement_backend.education.entity.Education;
import org.springframework.stereotype.Component;

@Component
public class EducationMapper {

    public EducationResponse toResponse(Education education) {
        return EducationResponse.builder()
                .id(education.getId())
                .educationType(education.getEducationType())
                .institutionName(education.getInstitutionName())
                .boardOrUniversity(education.getBoardOrUniversity())
                .specialization(education.getSpecialization())
                .percentage(education.getPercentage())
                .cgpa(education.getCgpa())
                .passingYear(education.getPassingYear())
                .build();
    }
}
