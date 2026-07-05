package com.nmamit.placement_backend.jobdrive.controller;

import com.nmamit.placement_backend.common.ApiResponse;
import com.nmamit.placement_backend.jobdrive.dto.response.JobDriveResponse;
import com.nmamit.placement_backend.jobdrive.service.JobDriveService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Job Drives", description = "Job drive listing for students")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/job-drives")
@RequiredArgsConstructor
public class StudentJobDriveController {

    private final JobDriveService jobDriveService;

    @Operation(summary = "List all open job drives")
    @GetMapping
    public ResponseEntity<ApiResponse<List<JobDriveResponse>>> getAllJobDrives() {
        return ResponseEntity.ok(ApiResponse.success(jobDriveService.getAllJobDrives()));
    }

    @Operation(summary = "Get job drive by ID")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<JobDriveResponse>> getJobDriveById(@PathVariable Long id) {
        return ResponseEntity.ok(ApiResponse.success(jobDriveService.getJobDriveById(id)));
    }
}
