package com.nmamit.placement_backend.Resume.controller;

import com.nmamit.placement_backend.Resume.dto.response.ResumeResponse;
import com.nmamit.placement_backend.Resume.service.ResumeService;
import com.nmamit.placement_backend.common.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@Tag(name = "Resume", description = "Resume upload and management for students")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/student/resume")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class ResumeController {

    private final ResumeService resumeService;

    @Operation(summary = "Upload resume (PDF only, max 5MB)")
    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ApiResponse<ResumeResponse>> uploadResume(
            Authentication authentication,
            @RequestParam("file") MultipartFile file) {
        return ResponseEntity.ok(ApiResponse.success("Resume uploaded successfully",
                resumeService.uploadResume(authentication.getName(), file)));
    }

    @Operation(summary = "Get current resume")
    @GetMapping
    public ResponseEntity<ApiResponse<ResumeResponse>> getResume(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(resumeService.getResume(authentication.getName())));
    }

    @Operation(summary = "Delete current resume")
    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> deleteResume(Authentication authentication) {
        resumeService.deleteResume(authentication.getName());
        return ResponseEntity.ok(ApiResponse.success("Resume deleted successfully", null));
    }
}