package com.nmamit.placement_backend.Application.service.impl;

import com.nmamit.placement_backend.Application.dto.request.ApplicationRequest;
import com.nmamit.placement_backend.Application.dto.response.ApplicationResponse;
import com.nmamit.placement_backend.Application.entity.Application;
import com.nmamit.placement_backend.Application.repository.ApplicationRepository;
import com.nmamit.placement_backend.Application.service.ApplicationService;
import com.nmamit.placement_backend.common.exception.BadRequestException;
import com.nmamit.placement_backend.common.exception.ResourceNotFoundException;
import com.nmamit.placement_backend.common.mapper.ApplicationMapper;
import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.enums.ApplicationStatus;
import com.nmamit.placement_backend.enums.JobDriveStatus;
import com.nmamit.placement_backend.jobdrive.entity.JobDrive;
import com.nmamit.placement_backend.jobdrive.repository.JobDriveRepository;
import com.nmamit.placement_backend.repository.UserAccountRepository;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import com.nmamit.placement_backend.student.repository.StudentProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ApplicationServiceImpl implements ApplicationService {

    private final ApplicationRepository applicationRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final JobDriveRepository jobDriveRepository;
    private final UserAccountRepository userAccountRepository;
    private final ApplicationMapper applicationMapper;

    private StudentProfile getStudentProfile(String collegeEmail) {
        UserAccount user = userAccountRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));
    }

    private JobDrive getJobDrive(Long id) {
        return jobDriveRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Job drive not found"));
    }

    private Application getApplication(Long id) {
        return applicationRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Application not found"));
    }

    @Override
    public ApplicationResponse applyForJob(String collegeEmail, ApplicationRequest request) {
        log.info("Student {} applying for job drive id: {}", collegeEmail, request.getJobDriveId());
        StudentProfile student = getStudentProfile(collegeEmail);
        JobDrive jobDrive = getJobDrive(request.getJobDriveId());

        if (jobDrive.getCompany().getActive() == null || !jobDrive.getCompany().getActive()) {
            throw new BadRequestException("Company is inactive.");
        }
        if (jobDrive.getStatus() != JobDriveStatus.OPEN) {
            throw new BadRequestException("Job drive is closed.");
        }
        if (LocalDate.now().isAfter(jobDrive.getRegistrationDeadline())) {
            throw new BadRequestException("Registration deadline has passed.");
        }
        if (applicationRepository.existsByStudentProfileAndJobDrive(student, jobDrive)) {
            throw new BadRequestException("You have already applied for this job.");
        }
        if (student.getCgpa().compareTo(jobDrive.getMinimumCgpa()) < 0) {
            throw new BadRequestException("Minimum CGPA requirement not met.");
        }
        if (student.getActiveBacklogs() > jobDrive.getAllowedBacklogs()) {
            throw new BadRequestException("Backlog criteria not met.");
        }

        Application application = Application.builder()
                .studentProfile(student)
                .jobDrive(jobDrive)
                .status(ApplicationStatus.APPLIED)
                .build();

        Application saved = applicationRepository.save(application);
        log.info("Application created with id: {}", saved.getId());
        return applicationMapper.toResponse(saved);
    }

    @Override
    public List<ApplicationResponse> getMyApplications(String collegeEmail) {
        log.debug("Fetching applications for: {}", collegeEmail);
        StudentProfile student = getStudentProfile(collegeEmail);
        return applicationRepository.findByStudentProfile(student)
                .stream()
                .map(applicationMapper::toResponse)
                .toList();
    }

    @Override
    public List<ApplicationResponse> getAllApplications() {
        log.debug("Fetching all applications");
        return applicationRepository.findAll()
                .stream()
                .map(applicationMapper::toResponse)
                .toList();
    }

    @Override
    public ApplicationResponse updateApplicationStatus(Long applicationId, ApplicationStatus status) {
        log.info("Updating application {} status to: {}", applicationId, status);
        Application application = getApplication(applicationId);
        if (application.getStatus() == status) {
            throw new BadRequestException("Application already has this status.");
        }
        application.setStatus(status);
        return applicationMapper.toResponse(applicationRepository.save(application));
    }
}
