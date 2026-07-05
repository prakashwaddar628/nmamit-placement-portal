package com.nmamit.placement_backend.education.controller;

import com.nmamit.placement_backend.common.ApiResponse;
import com.nmamit.placement_backend.education.dto.request.EducationRequest;
import com.nmamit.placement_backend.education.dto.response.EducationResponse;
import com.nmamit.placement_backend.education.service.EducationService;
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

@Tag(name = "Education", description = "Student education records management")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/student/education")
@RequiredArgsConstructor
@PreAuthorize("hasRole('STUDENT')")
public class EducationController {

    private final EducationService educationService;

    @Operation(summary = "Add an education record")
    @PostMapping
    public ResponseEntity<ApiResponse<EducationResponse>> addEducation(
            Authentication authentication,
            @Valid @RequestBody EducationRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Education added",
                        educationService.addEducation(authentication.getName(), request)));
    }

    @Operation(summary = "Get all education records")
    @GetMapping
    public ResponseEntity<ApiResponse<List<EducationResponse>>> getEducation(Authentication authentication) {
        return ResponseEntity.ok(ApiResponse.success(educationService.getEducation(authentication.getName())));
    }

    @Operation(summary = "Update an education record")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<EducationResponse>> updateEducation(
            Authentication authentication,
            @PathVariable Long id,
            @Valid @RequestBody EducationRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Education updated",
                educationService.updateEducation(authentication.getName(), id, request)));
    }

    @Operation(summary = "Delete an education record")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteEducation(
            Authentication authentication,
            @PathVariable Long id) {
        educationService.deleteEducation(authentication.getName(), id);
        return ResponseEntity.ok(ApiResponse.success("Education deleted", null));
    }
}
