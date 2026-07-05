package com.nmamit.placement_backend.jobdrive.controller;

import com.nmamit.placement_backend.common.ApiResponse;
import com.nmamit.placement_backend.jobdrive.dto.request.JobDriveRequest;
import com.nmamit.placement_backend.jobdrive.dto.response.JobDriveResponse;
import com.nmamit.placement_backend.jobdrive.service.JobDriveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin - Job Drives", description = "Job drive management for admins")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/admin/job-drives")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminJobDriveController {

    private final JobDriveService jobDriveService;

    @Operation(summary = "Create a new job drive")
    @PostMapping
    public ResponseEntity<ApiResponse<JobDriveResponse>> createJobDrive(
            @Valid @RequestBody JobDriveRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Job drive created", jobDriveService.createJobDrive(request)));
    }

    @Operation(summary = "Update a job drive")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<JobDriveResponse>> updateJobDrive(
            @PathVariable Long id,
            @Valid @RequestBody JobDriveRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Job drive updated", jobDriveService.updateJobDrive(id, request)));
    }

    @Operation(summary = "Close a job drive")
    @PatchMapping("/{id}/close")
    public ResponseEntity<ApiResponse<JobDriveResponse>> closeJobDrive(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success("Job drive closed", jobDriveService.closeJobDrive(id)));
    }
}