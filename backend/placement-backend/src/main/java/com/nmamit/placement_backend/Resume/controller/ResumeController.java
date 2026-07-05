package com.nmamit.placement_backend.Resume.controller;

import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.nmamit.placement_backend.Resume.service.ResumeService;
import com.nmamit.placement_backend.Resume.dto.response.ResumeResponse;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.Authentication;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/student/resume")
@RequiredArgsConstructor
public class ResumeController {

    private final ResumeService resumeService;

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<ResumeResponse> uploadResume(
            Authentication authentication,
            @RequestParam("file") MultipartFile file) {

        return ResponseEntity.ok(
                resumeService.uploadResume(
                        authentication.getName(),
                        file));
    }

    @GetMapping
    public ResponseEntity<ResumeResponse> getResume(
            Authentication authentication) {

        return ResponseEntity.ok(
                resumeService.getResume(
                        authentication.getName()));
    }

    @DeleteMapping
    public ResponseEntity<String> deleteResume(
            Authentication authentication) {

        resumeService.deleteResume(authentication.getName());

        return ResponseEntity.ok("Resume deleted successfully.");
    }
}