package com.nmamit.placement_backend.jobdrive.service;

import com.nmamit.placement_backend.jobdrive.dto.response.JobDriveResponse;
import com.nmamit.placement_backend.jobdrive.dto.request.JobDriveRequest;

import java.util.List;

public interface JobDriveService {
    
    JobDriveResponse createJobDrive(JobDriveRequest request);

    List<JobDriveResponse> getAllJobDrives();

    JobDriveResponse getJobDriveById(Long id);

    JobDriveResponse updateJobDrive(Long id, JobDriveRequest request);

    JobDriveResponse closeJobDrive(Long id);
}
