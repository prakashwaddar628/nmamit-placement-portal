package com.nmamit.placement_backend.dashboard;

import com.nmamit.placement_backend.Application.repository.ApplicationRepository;
import com.nmamit.placement_backend.Resume.repository.ResumeRepository;
import com.nmamit.placement_backend.company.repository.CompanyRepository;
import com.nmamit.placement_backend.dashboard.dto.AdminDashboardDTO;
import com.nmamit.placement_backend.dashboard.dto.StudentDashboardDTO;
import com.nmamit.placement_backend.dashboard.service.impl.DashboardServiceImpl;
import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.enums.ApplicationStatus;
import com.nmamit.placement_backend.enums.JobDriveStatus;
import com.nmamit.placement_backend.enums.Role;
import com.nmamit.placement_backend.jobdrive.entity.JobDrive;
import com.nmamit.placement_backend.jobdrive.repository.JobDriveRepository;
import com.nmamit.placement_backend.repository.UserAccountRepository;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import com.nmamit.placement_backend.student.repository.StudentProfileRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("DashboardServiceImpl Tests")
class DashboardServiceTest {

    @Mock private ApplicationRepository applicationRepository;
    @Mock private ResumeRepository resumeRepository;
    @Mock private StudentProfileRepository studentProfileRepository;
    @Mock private UserAccountRepository userAccountRepository;
    @Mock private CompanyRepository companyRepository;
    @Mock private JobDriveRepository jobDriveRepository;

    @InjectMocks
    private DashboardServiceImpl dashboardService;

    private UserAccount user;
    private StudentProfile profile;

    @BeforeEach
    void setUp() {
        user = UserAccount.builder()
                .id(1L)
                .collegeEmail("test@nmamit.in")
                .role(Role.STUDENT)
                .build();

        profile = StudentProfile.builder()
                .id(1L)
                .user(user)
                .fullName("Test Student")
                .usn("4NM21CS001")
                .mobile("9876543210")
                .cgpa(BigDecimal.valueOf(8.5))
                .branch("CSE")
                .build();
    }

    @Test
    @DisplayName("Should return student dashboard with correct counts")
    void shouldReturnStudentDashboard() {
        when(userAccountRepository.findByCollegeEmail("test@nmamit.in")).thenReturn(Optional.of(user));
        when(studentProfileRepository.findByUser(user)).thenReturn(Optional.of(profile));
        when(resumeRepository.existsByStudentProfile(profile)).thenReturn(true);
        when(applicationRepository.countByStudentProfile(profile)).thenReturn(4L);
        when(applicationRepository.countByStudentProfileAndStatus(profile, ApplicationStatus.SELECTED)).thenReturn(1L);
        when(applicationRepository.countByStudentProfileAndStatus(profile, ApplicationStatus.INTERVIEW)).thenReturn(1L);
        when(jobDriveRepository.findByStatusAndDriveDateGreaterThanEqual(
                eq(JobDriveStatus.OPEN), any(LocalDate.class)))
                .thenReturn(List.of(JobDrive.builder().build(), JobDrive.builder().build()));

        StudentDashboardDTO result = dashboardService.getStudentDashboard("test@nmamit.in");

        assertThat(result.isProfileCompleted()).isTrue();
        assertThat(result.isResumeUploaded()).isTrue();
        assertThat(result.getApplications()).isEqualTo(4L);
        assertThat(result.getSelected()).isEqualTo(1L);
        assertThat(result.getInterviews()).isEqualTo(1L);
        assertThat(result.getUpcomingDrives()).isEqualTo(2L);
    }

    @Test
    @DisplayName("Should return admin dashboard with aggregate stats")
    void shouldReturnAdminDashboard() {
        when(studentProfileRepository.count()).thenReturn(100L);
        when(companyRepository.countByActiveTrue()).thenReturn(10L);
        when(jobDriveRepository.count()).thenReturn(20L);
        when(applicationRepository.count()).thenReturn(350L);
        when(applicationRepository.countByStatus(ApplicationStatus.SELECTED)).thenReturn(85L);
        when(jobDriveRepository.countByStatus(JobDriveStatus.OPEN)).thenReturn(12L);
        when(jobDriveRepository.countByStatus(JobDriveStatus.CLOSED)).thenReturn(8L);

        AdminDashboardDTO result = dashboardService.getAdminDashboard();

        assertThat(result.getStudents()).isEqualTo(100L);
        assertThat(result.getCompanies()).isEqualTo(10L);
        assertThat(result.getJobDrives()).isEqualTo(20L);
        assertThat(result.getApplications()).isEqualTo(350L);
        assertThat(result.getSelectedStudents()).isEqualTo(85L);
        assertThat(result.getOpenDrives()).isEqualTo(12L);
        assertThat(result.getClosedDrives()).isEqualTo(8L);
    }

    @Test
    @DisplayName("Should return profileCompleted=false when profile is incomplete")
    void shouldReturnProfileIncomplete() {
        StudentProfile incompleteProfile = StudentProfile.builder()
                .id(2L)
                .user(user)
                .fullName("Incomplete User")
                .build(); // missing usn, mobile, cgpa, branch

        when(userAccountRepository.findByCollegeEmail("test@nmamit.in")).thenReturn(Optional.of(user));
        when(studentProfileRepository.findByUser(user)).thenReturn(Optional.of(incompleteProfile));
        when(resumeRepository.existsByStudentProfile(incompleteProfile)).thenReturn(false);
        when(applicationRepository.countByStudentProfile(incompleteProfile)).thenReturn(0L);
        when(applicationRepository.countByStudentProfileAndStatus(any(), any())).thenReturn(0L);
        when(jobDriveRepository.findByStatusAndDriveDateGreaterThanEqual(any(), any())).thenReturn(List.of());

        StudentDashboardDTO result = dashboardService.getStudentDashboard("test@nmamit.in");

        assertThat(result.isProfileCompleted()).isFalse();
        assertThat(result.isResumeUploaded()).isFalse();
    }
}
