package com.nmamit.placement_backend.Application.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import com.nmamit.placement_backend.Application.dto.request.ApplicationRequest;
import com.nmamit.placement_backend.Application.dto.response.ApplicationResponse;
import com.nmamit.placement_backend.Application.service.ApplicationService;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RestController
@RequestMapping("/api/student/applications")
@RequiredArgsConstructor
public class StudentApplicationController {

    private final ApplicationService applicationService;

    @PostMapping
    public ResponseEntity<ApplicationResponse> applyForJob(
            Authentication authentication,
            @Valid @RequestBody ApplicationRequest request) {

        return ResponseEntity.ok(
                applicationService.applyForJob(
                        authentication.getName(),
                        request));
    }

    @GetMapping
    public ResponseEntity<List<ApplicationResponse>> getMyApplications(
            Authentication authentication) {

        return ResponseEntity.ok(
                applicationService.getMyApplications(
                        authentication.getName()));
    }
}
