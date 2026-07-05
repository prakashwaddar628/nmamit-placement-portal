package com.nmamit.placement_backend.jobdrive.controller;

import com.nmamit.placement_backend.jobdrive.service.JobDriveService;
import com.nmamit.placement_backend.jobdrive.dto.response.JobDriveResponse;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/job-drives")
@RequiredArgsConstructor
public class StudentJobDriveController {
    
    private final JobDriveService jobDriveSrvice;

    @GetMapping
    public ResponseEntity<List<JobDriveResponse>> getAllJobDrives() {
        return ResponseEntity.ok(jobDriveSrvice.getAllJobDrives());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobDriveResponse> getJobDriveById(@PathVariable Long id) {
        return ResponseEntity.ok(jobDriveSrvice.getJobDriveById(id));
    }
}
