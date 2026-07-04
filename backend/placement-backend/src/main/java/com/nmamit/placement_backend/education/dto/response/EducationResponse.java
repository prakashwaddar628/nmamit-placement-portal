package com.nmamit.placement_backend.education.dto.response;

import com.nmamit.placement_backend.enums.EducationType;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationResponse {
    
    private Long id;
    private EducationType educationType;
    private String institutionName;
    private String boardOrUniversity;
    private String specialization;
    private BigDecimal percentage;
    private BigDecimal cgpa;
    private Integer passingYear;
    
}
