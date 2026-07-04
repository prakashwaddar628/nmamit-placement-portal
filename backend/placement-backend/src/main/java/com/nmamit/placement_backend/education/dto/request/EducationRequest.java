package com.nmamit.placement_backend.education.dto.request;

import com.nmamit.placement_backend.enums.EducationType;
import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class EducationRequest {
    
    private EducationType educationType;
    private String institutionName;
    private String boardOrUniversity;
    private String specialization;
    private BigDecimal percentage;
    private BigDecimal cgpa;
    private Integer passingYear;
}
