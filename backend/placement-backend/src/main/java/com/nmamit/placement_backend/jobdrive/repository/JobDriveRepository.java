package com.nmamit.placement_backend.jobdrive.repository;

import com.nmamit.placement_backend.jobdrive.entity.JobDrive;
import com.nmamit.placement_backend.enums.JobDriveStatus;
import com.nmamit.placement_backend.company.entity.Company;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;


public interface JobDriveRepository extends JpaRepository<JobDrive, Long> {
    
    List<JobDrive> findByStatus(JobDriveStatus status);

    List<JobDrive> findByCompany(Company company);
}
