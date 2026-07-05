package com.nmamit.placement_backend.jobdrive.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;

import com.nmamit.placement_backend.enums.JobDriveStatus;
import com.nmamit.placement_backend.enums.JobType;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDriveResponse {

    private Long id;

    private String companyName;

    private String jobRole;

    private JobType jobType;

    private BigDecimal packageLpa;

    private String location;

    private LocalDate driveDate;

    private LocalDate registrationDeadline;

    private BigDecimal minimumCgpa;

    private Integer allowedBacklogs;

    private String description;

    private JobDriveStatus status;
    
}
