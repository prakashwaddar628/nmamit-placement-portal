package com.nmamit.placement_backend.dashboard.service.impl;

import com.nmamit.placement_backend.Application.repository.ApplicationRepository;
import com.nmamit.placement_backend.Resume.repository.ResumeRepository;
import com.nmamit.placement_backend.company.repository.CompanyRepository;
import com.nmamit.placement_backend.dashboard.dto.AdminDashboardDTO;
import com.nmamit.placement_backend.dashboard.dto.StudentDashboardDTO;
import com.nmamit.placement_backend.dashboard.service.DashboardService;
import com.nmamit.placement_backend.common.exception.ResourceNotFoundException;
import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.enums.ApplicationStatus;
import com.nmamit.placement_backend.enums.JobDriveStatus;
import com.nmamit.placement_backend.jobdrive.repository.JobDriveRepository;
import com.nmamit.placement_backend.repository.UserAccountRepository;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import com.nmamit.placement_backend.student.repository.StudentProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Slf4j
@Service
@RequiredArgsConstructor
public class DashboardServiceImpl implements DashboardService {

    private final ApplicationRepository applicationRepository;
    private final ResumeRepository resumeRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserAccountRepository userAccountRepository;
    private final CompanyRepository companyRepository;
    private final JobDriveRepository jobDriveRepository;

    @Override
    public StudentDashboardDTO getStudentDashboard(String collegeEmail) {
        log.info("Fetching student dashboard for: {}", collegeEmail);

        UserAccount user = userAccountRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        StudentProfile profile = studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));

        boolean profileCompleted = profile.getFullName() != null
                && profile.getUsn() != null
                && profile.getMobile() != null
                && profile.getCgpa() != null
                && profile.getBranch() != null;

        boolean resumeUploaded = resumeRepository.existsByStudentProfile(profile);

        long applications = applicationRepository.countByStudentProfile(profile);
        long selected = applicationRepository.countByStudentProfileAndStatus(profile, ApplicationStatus.SELECTED);
        long interviews = applicationRepository.countByStudentProfileAndStatus(profile, ApplicationStatus.INTERVIEW);
        long upcomingDrives = jobDriveRepository
                .findByStatusAndDriveDateGreaterThanEqual(JobDriveStatus.OPEN, LocalDate.now())
                .size();

        return StudentDashboardDTO.builder()
                .profileCompleted(profileCompleted)
                .resumeUploaded(resumeUploaded)
                .applications(applications)
                .selected(selected)
                .interviews(interviews)
                .upcomingDrives(upcomingDrives)
                .build();
    }

    @Override
    public AdminDashboardDTO getAdminDashboard() {
        log.info("Fetching admin dashboard statistics");

        long students = studentProfileRepository.count();
        long companies = companyRepository.countByActiveTrue();
        long jobDrives = jobDriveRepository.count();
        long applications = applicationRepository.count();
        long selectedStudents = applicationRepository.countByStatus(ApplicationStatus.SELECTED);
        long openDrives = jobDriveRepository.countByStatus(JobDriveStatus.OPEN);
        long closedDrives = jobDriveRepository.countByStatus(JobDriveStatus.CLOSED);

        return AdminDashboardDTO.builder()
                .students(students)
                .companies(companies)
                .jobDrives(jobDrives)
                .applications(applications)
                .selectedStudents(selectedStudents)
                .openDrives(openDrives)
                .closedDrives(closedDrives)
                .build();
    }
}
