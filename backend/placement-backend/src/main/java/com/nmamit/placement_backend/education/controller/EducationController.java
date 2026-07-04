package com.nmamit.placement_backend.education.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.nmamit.placement_backend.education.service.EducationService;
import com.nmamit.placement_backend.education.dto.request.EducationRequest;
import com.nmamit.placement_backend.education.dto.response.EducationResponse;

import lombok.RequiredArgsConstructor;
import java.util.List;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;



@RestController
@RequestMapping("/api/student/education")
@RequiredArgsConstructor
public class EducationController {
    
    private final EducationService educationService;

    @PostMapping
    public ResponseEntity<EducationResponse> addEducation(
            Authentication authentication,
            @RequestBody EducationRequest request){

            return ResponseEntity.ok(
                educationService.addEducation(
                    authentication.getName(), 
                    request)
            );
    }

    @GetMapping
    public ResponseEntity<List<EducationResponse>> getEducation(
            Authentication authentication){

                return ResponseEntity.ok(
                    educationService.getEducation(
                        authentication.getName()
                    )
                );
    }

    @PutMapping("/{id}")
    public ResponseEntity<EducationResponse> updateEducation(
            Authentication authentication,
            @PathVariable Long id,
            @RequestBody EducationRequest request){

                return ResponseEntity.ok(
                    educationService.updateEducation(
                        authentication.getName(),
                        id,
                        request
                    )
                );
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteEducation(
            Authentication authentication,
            @PathVariable Long id) {

        educationService.deleteEducation(
                authentication.getName(),
                id);

        return ResponseEntity.noContent().build();
    }
}
