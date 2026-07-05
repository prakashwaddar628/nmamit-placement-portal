package com.nmamit.placement_backend.jobdrive.service.impl;

import com.nmamit.placement_backend.jobdrive.service.JobDriveService;
import com.nmamit.placement_backend.jobdrive.repository.JobDriveRepository;
import com.nmamit.placement_backend.company.repository.CompanyRepository;
import com.nmamit.placement_backend.enums.JobDriveStatus;
import com.nmamit.placement_backend.company.entity.Company;
import com.nmamit.placement_backend.common.exception.*;
import com.nmamit.placement_backend.jobdrive.dto.request.JobDriveRequest;
import com.nmamit.placement_backend.jobdrive.dto.response.JobDriveResponse;
import com.nmamit.placement_backend.jobdrive.entity.JobDrive;

import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

import lombok.*;

@Service
@RequiredArgsConstructor
public class JobDriveServiceImpl implements JobDriveService {

    private final JobDriveRepository jobDriveRepository;
    private final CompanyRepository companyRepository;

    // helper method 1
    private Company getCompany(Long companyId) {

        Company company = companyRepository.findById(companyId)
        .orElseThrow(() -> new ResourceNotFoundException("Company not found"));

        if (!Boolean.TRUE.equals(company.getActive())){
            throw new BadRequestException("Company is not active");
        }

        return company;
    }

    // helper method 2
    private JobDrive getJobDrive(Long id) {
        return jobDriveRepository.findById(id)
        .orElseThrow(() -> new ResourceNotFoundException("Job drive not found"));
    }

    // helper method 3
    private JobDriveResponse mapToResponse(JobDrive jobDrive) {

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

    @Override
    public JobDriveResponse createJobDrive(JobDriveRequest request) {

        Company company = getCompany(request.getCompanyId());

        if (request.getRegistrationDeadline().isAfter(request.getDriveDate())) {
            throw new BadRequestException(
                "Registration deadline cannot be after the drive date."
            );
        }

        if (request.getDriveDate().isBefore(LocalDate.now())) {
            throw new BadRequestException(
                "Drive date cannot be in the past."
            );
        }

        JobDrive jobDrive = JobDrive.builder()
                .company(company)
                .jobRole(request.getJobRole())
                .jobType(request.getJobType())
                .packageLpa(request.getPackageLpa())
                .location(request.getLocation())
                .driveDate(request.getDriveDate())
                .registrationDeadline(request.getRegistrationDeadline())
                .minimumCgpa(request.getMinimumCgpa())
                .allowedBacklogs(request.getAllowedBacklogs())
                .description(request.getDescription())
                .status(JobDriveStatus.OPEN)
                .build();

        JobDrive saved = jobDriveRepository.save(jobDrive);

        return mapToResponse(saved);
    }

    @Override
    public List<JobDriveResponse> getAllJobDrives() {
        return jobDriveRepository.findByStatus(JobDriveStatus.OPEN)
        .stream()
        .map(this::mapToResponse)
        .toList();
    }

    @Override
    public JobDriveResponse getJobDriveById(Long id) {
        return mapToResponse(getJobDrive(id));
    }

    @Override
    public JobDriveResponse updateJobDrive(Long id, JobDriveRequest request) {

        JobDrive jobDrive = getJobDrive(id);
        
        jobDrive.setJobRole(request.getJobRole());
        jobDrive.setJobType(request.getJobType());
        jobDrive.setPackageLpa(request.getPackageLpa());
        jobDrive.setLocation(request.getLocation());
        jobDrive.setDriveDate(request.getDriveDate());
        jobDrive.setRegistrationDeadline(request.getRegistrationDeadline());
        jobDrive.setMinimumCgpa(request.getMinimumCgpa());
        jobDrive.setAllowedBacklogs(request.getAllowedBacklogs());
        jobDrive.setDescription(request.getDescription());

        return mapToResponse(jobDriveRepository.save(jobDrive));

    }

    @Override
    public JobDriveResponse closeJobDrive(Long id) {

        JobDrive jobDrive = getJobDrive(id);
        
        if (jobDrive.getStatus() == JobDriveStatus.CLOSED) {
            throw new BadRequestException("Job drive is already closed.");
        }

        jobDrive.setStatus(JobDriveStatus.CLOSED);

        return mapToResponse(jobDriveRepository.save(jobDrive));
    }


}
