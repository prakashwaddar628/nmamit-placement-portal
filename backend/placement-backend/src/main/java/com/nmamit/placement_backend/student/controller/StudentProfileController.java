package com.nmamit.placement_backend.student.controller;

import com.nmamit.placement_backend.common.ApiResponse;
import com.nmamit.placement_backend.student.dto.request.StudentProfileRequest;
import com.nmamit.placement_backend.student.dto.response.StudentProfileResponse;
import com.nmamit.placement_backend.student.service.StudentProfileService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Student Profile", description = "Student profile management")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    @Operation(summary = "Get current student profile")
    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<StudentProfileResponse>> getProfile(Authentication authentication) {
        String email = authentication.getName();
        return ResponseEntity.ok(ApiResponse.success(studentProfileService.getProfile(email)));
    }

    @Operation(summary = "Update current student profile")
    @PutMapping("/profile")
    public ResponseEntity<ApiResponse<StudentProfileResponse>> updateProfile(
            Authentication authentication,
            @Valid @RequestBody StudentProfileRequest request) {
        String email = authentication.getName();
        return ResponseEntity.ok(ApiResponse.success("Profile updated successfully",
                studentProfileService.updateProfile(email, request)));
    }
}