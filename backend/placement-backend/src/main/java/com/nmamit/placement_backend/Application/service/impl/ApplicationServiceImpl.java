package com.nmamit.placement_backend.Application.service.impl;

import java.time.LocalDate;

import org.springframework.stereotype.Service;

import com.nmamit.placement_backend.Application.dto.request.ApplicationRequest;
import com.nmamit.placement_backend.Application.dto.response.ApplicationResponse;
import com.nmamit.placement_backend.Application.entity.Application;
import com.nmamit.placement_backend.Application.repository.ApplicationRepository;
import com.nmamit.placement_backend.Application.service.ApplicationService;
import com.nmamit.placement_backend.common.exception.ResourceNotFoundException;
import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.enums.ApplicationStatus;
import com.nmamit.placement_backend.enums.JobDriveStatus;
import com.nmamit.placement_backend.jobdrive.entity.JobDrive;
import com.nmamit.placement_backend.jobdrive.repository.JobDriveRepository;
import com.nmamit.placement_backend.repository.UserAccountRepository;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import com.nmamit.placement_backend.student.repository.StudentProfileRepository;
import com.nmamit.placement_backend.common.exception.BadRequestException;

import java.util.List;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {
    
    private final ApplicationRepository applicationRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final JobDriveRepository jobDriveRepository;
    private final UserAccountRepository userAccountRepository;

    private StudentProfile getStudentProfile(String collegeEmail) {

        UserAccount user = userAccountRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() ->
                        new ResourceNotFoundException("User not found"));

        return studentProfileRepository.findByUser(user)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Student profile not found"));
    }

    private JobDrive getJobDrive(Long id) {

        return jobDriveRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Job drive not found"));
    }

    private Application getApplication(Long id) {

        return applicationRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Application not found"));
    }

    private ApplicationResponse mapToResponse(Application application) {

        return ApplicationResponse.builder()
                .id(application.getId())
                .companyName(application.getJobDrive().getCompany().getCompanyName())
                .jobRole(application.getJobDrive().getJobRole())
                .status(application.getStatus())
                .appliedAt(application.getAppliedAt())
                .build();
    }

    @Override
    public ApplicationResponse applyForJob(
        String collegeEmail, 
        ApplicationRequest request) {

        StudentProfile student = getStudentProfile(collegeEmail);
        JobDrive jobDrive = getJobDrive(request.getJobDriveId());

        if (jobDrive.getCompany().getActive() == null ||
            !jobDrive.getCompany().getActive()) {

            throw new BadRequestException(
                    "Company is inactive.");
        }
        
        if (jobDrive.getStatus() != JobDriveStatus.OPEN) {
            throw new BadRequestException("Job drive is closed.");
        }

        if (LocalDate.now().isAfter(jobDrive.getRegistrationDeadline())) {
            throw new BadRequestException(
                    "Registration deadline has passed.");
        }

        if (applicationRepository.existsByStudentProfileAndJobDrive(student, jobDrive)) {
            throw new BadRequestException(
                    "You have already applied for this job.");
        }

        if (student.getCgpa().compareTo(jobDrive.getMinimumCgpa()) < 0) {
            throw new BadRequestException(
                    "Minimum CGPA requirement not met.");
        }

        if (student.getActiveBacklogs() > jobDrive.getAllowedBacklogs()) {
            throw new BadRequestException(
                    "Backlog criteria not met.");
        }

        Application application = Application.builder()
                .studentProfile(student)
                .jobDrive(jobDrive)
                .status(ApplicationStatus.APPLIED)
                .build();

        Application saved = applicationRepository.save(application);

        return mapToResponse(saved);
    }

    @Override
    public List<ApplicationResponse> getMyApplications(String collegeEmail) {

        StudentProfile student = getStudentProfile(collegeEmail);

        return applicationRepository.findByStudentProfile(student)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public List<ApplicationResponse> getAllApplications() {

        return applicationRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public ApplicationResponse updateApplicationStatus(
            Long applicationId,
            ApplicationStatus status) {

        Application application = getApplication(applicationId);

        if (application.getStatus() == status) {
            throw new BadRequestException(
                    "Application already has this status.");
        }

        Application updated = applicationRepository.save(application);

        return mapToResponse(updated);
    }

}

