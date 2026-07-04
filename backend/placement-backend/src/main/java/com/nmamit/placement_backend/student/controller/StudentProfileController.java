package com.nmamit.placement_backend.student.controller;

import com.nmamit.placement_backend.student.dto.request.StudentProfileRequest;
import com.nmamit.placement_backend.student.dto.response.StudentProfileResponse;
import com.nmamit.placement_backend.student.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/student")
@RequiredArgsConstructor
public class StudentProfileController {

    private final StudentProfileService studentProfileService;

    @GetMapping("/profile")
    public ResponseEntity<StudentProfileResponse> getProfile(
            Authentication authentication) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                studentProfileService.getProfile(email));
    }

    @PutMapping("/profile")
    public ResponseEntity<StudentProfileResponse> updateProfile(
            Authentication authentication,
            @RequestBody StudentProfileRequest request) {

        String email = authentication.getName();

        return ResponseEntity.ok(
                studentProfileService.updateProfile(email, request));
    }
}