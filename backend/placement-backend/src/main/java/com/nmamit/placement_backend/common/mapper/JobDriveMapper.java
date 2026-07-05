package com.nmamit.placement_backend.common.mapper;

import com.nmamit.placement_backend.jobdrive.dto.response.JobDriveResponse;
import com.nmamit.placement_backend.jobdrive.entity.JobDrive;
import org.springframework.stereotype.Component;

@Component
public class JobDriveMapper {

    public JobDriveResponse toResponse(JobDrive jobDrive) {
        return JobDriveResponse.builder()
                .id(jobDrive.getId())
                .companyName(jobDrive.getCompany().getCompanyName())
                .jobRole(jobDrive.getJobRole())
                .jobType(jobDrive.getJobType())
                .packageLpa(jobDrive.getPackageLpa())
                .location(jobDrive.getLocation())
                .driveDate(jobDrive.getDriveDate())
                .registrationDeadline(jobDrive.getRegistrationDeadline())
                .minimumCgpa(jobDrive.getMinimumCgpa())
                .allowedBacklogs(jobDrive.getAllowedBacklogs())
                .description(jobDrive.getDescription())
                .status(jobDrive.getStatus())
                .build();
    }
}
