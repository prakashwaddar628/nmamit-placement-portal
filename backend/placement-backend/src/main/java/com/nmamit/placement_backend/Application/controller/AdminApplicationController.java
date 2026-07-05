package com.nmamit.placement_backend.Application.controller;

import com.nmamit.placement_backend.Application.dto.request.UpdateApplicationStatusRequest;
import com.nmamit.placement_backend.Application.dto.response.ApplicationResponse;
import com.nmamit.placement_backend.Application.service.ApplicationService;
import com.nmamit.placement_backend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "Admin - Applications", description = "Application management for admins")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/admin/applications")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminApplicationController {

    private final ApplicationService applicationService;

    @Operation(summary = "Get all applications")
    @GetMapping
    public ResponseEntity<ApiResponse<List<ApplicationResponse>>> getAllApplications() {
        return ResponseEntity.ok(ApiResponse.success(applicationService.getAllApplications()));
    }

    @Operation(summary = "Update application status")
    @PatchMapping("/{id}/status")
    public ResponseEntity<ApiResponse<ApplicationResponse>> updateApplicationStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateApplicationStatusRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Status updated",
                applicationService.updateApplicationStatus(id, request.getStatus())));
    }
}