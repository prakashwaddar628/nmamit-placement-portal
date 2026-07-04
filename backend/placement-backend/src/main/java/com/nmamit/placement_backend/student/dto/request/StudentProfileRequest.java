package com.nmamit.placement_backend.student.dto.request;

import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfileRequest {
    private String usn;
    
    private String fullName;
    
    private String mobile;
    
    private String alternateEmail;
    
    private LocalDate dateOfBirth;
    
    private String gender;
    
    private String department;
    
    private String branch;
    
    private Integer currentSemester;
    
    private BigDecimal cgpa;
    
    private Integer activeBacklogs;
    
    private String address;
    
    private String city;
    
    private String state;
    
    private String country;
    
    private String pincode;
    
    private String linkedinUrl;
    
    private String githubUrl;
    
    private String portfolioUrl;

}