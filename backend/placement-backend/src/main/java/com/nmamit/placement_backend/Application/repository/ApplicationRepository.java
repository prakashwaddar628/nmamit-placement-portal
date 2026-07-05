package com.nmamit.placement_backend.Application.repository;

import com.nmamit.placement_backend.Application.entity.Application;
import com.nmamit.placement_backend.enums.ApplicationStatus;
import com.nmamit.placement_backend.jobdrive.entity.JobDrive;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application, Long> {

    boolean existsByStudentProfileAndJobDrive(StudentProfile studentProfile, JobDrive jobDrive);

    List<Application> findByStudentProfile(StudentProfile studentProfile);

    List<Application> findByJobDrive(JobDrive jobDrive);

    long countByStudentProfile(StudentProfile studentProfile);

    long countByStudentProfileAndStatus(StudentProfile studentProfile, ApplicationStatus status);

    long countByStatus(ApplicationStatus status);
}
