package com.nmamit.placement_backend.jobdrive.dto.request;

import com.nmamit.placement_backend.enums.JobType;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDriveRequest {
    
    @NotNull
    private Long companyId;

    @NotBlank
    private String jobRole;

    @NotNull
    private JobType jobType;

    @DecimalMin("0.0")
    private BigDecimal packageLpa;

    private String location;

    @NotNull
    private LocalDate driveDate;

    @NotNull
    private LocalDate registrationDeadline;

    @DecimalMin("0.0")
    private BigDecimal minimumCgpa;

    @Min(0)
    private Integer allowedBacklogs;

    private String description;
}
