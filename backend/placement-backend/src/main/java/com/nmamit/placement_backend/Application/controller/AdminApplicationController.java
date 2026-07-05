package com.nmamit.placement_backend.Application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nmamit.placement_backend.Application.dto.request.UpdateApplicationStatusRequest;
import com.nmamit.placement_backend.Application.dto.response.ApplicationResponse;
import com.nmamit.placement_backend.Application.service.ApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/admin/applications")
@RequiredArgsConstructor
public class AdminApplicationController {

    private final ApplicationService applicationService;

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getAllApplications() {

        return ResponseEntity.ok(
                applicationService.getAllApplications());
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<ApplicationResponse> updateApplicationStatus(
            @PathVariable Long id,
            @Valid @RequestBody UpdateApplicationStatusRequest request) {

        return ResponseEntity.ok(
                applicationService.updateApplicationStatus(
                        id,
                        request.getStatus()));
    }
}