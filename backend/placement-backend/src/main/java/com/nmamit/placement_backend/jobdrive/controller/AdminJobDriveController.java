package com.nmamit.placement_backend.jobdrive.controller;

import com.nmamit.placement_backend.jobdrive.dto.request.JobDriveRequest;
import com.nmamit.placement_backend.jobdrive.dto.response.JobDriveResponse;
import com.nmamit.placement_backend.jobdrive.service.JobDriveService;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/admin/job-drives")
@RequiredArgsConstructor
public class AdminJobDriveController {

    private final JobDriveService jobDriveService;
    
    @PostMapping
    public ResponseEntity<JobDriveResponse> createJobDrive(
            @Valid @RequestBody JobDriveRequest request) {

        return ResponseEntity.ok(
                jobDriveService.createJobDrive(request));
    }

    @PutMapping("/{id}")
    public ResponseEntity<JobDriveResponse> updateJobDrive(
            @PathVariable Long id,
            @Valid @RequestBody JobDriveRequest request) {

        return ResponseEntity.ok(
                jobDriveService.updateJobDrive(id, request));
    }

    @PatchMapping("/{id}/close")
    public ResponseEntity<JobDriveResponse> closeJobDrive(
            @PathVariable Long id) {

        return ResponseEntity.ok(
                jobDriveService.closeJobDrive(id));
    }
}