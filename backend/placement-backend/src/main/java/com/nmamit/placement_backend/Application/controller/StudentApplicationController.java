package com.nmamit.placement_backend.Application.controller;

import com.nmamit.placement_backend.Application.dto.request.ApplicationRequest;
import com.nmamit.placement_backend.Application.dto.response.ApplicationResponse;
import com.nmamit.placement_backend.Application.service.ApplicationService;
import com.nmamit.placement_backend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Student Applications", description = "Job application management for students")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/student/applications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class StudentApplicationController {

    private final ApplicationService applicationService;

    @Operation(summary = "Apply for a job drive")
    @PostMapping
    public ResponseEntity<ApiResponse<ApplicationResponse>> applyForJob(
            Authentication authentication,
            @Valid @RequestBody ApplicationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Application submitted successfully",
                        applicationService.applyForJob(authentication.getName(), request)));
    }

    @Operation(summary = "Get my applications")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>> getMyApplications(
            Authentication authentication) {
        return ResponseEntity.ok(
                ApiResponse.success(applicationService.getMyApplications(authentication.getName())));
    }
}
