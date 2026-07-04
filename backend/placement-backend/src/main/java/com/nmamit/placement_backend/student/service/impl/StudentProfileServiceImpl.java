package com.nmamit.placement_backend.student.service.impl;

import org.springframework.stereotype.Service;

import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.repository.UserAccountRepository;
import com.nmamit.placement_backend.student.dto.request.StudentProfileRequest;
import com.nmamit.placement_backend.student.dto.response.StudentProfileResponse;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import com.nmamit.placement_backend.student.repository.StudentProfileRepository;
import com.nmamit.placement_backend.student.service.StudentProfileService;

import lombok.*;

@Service
@RequiredArgsConstructor
public class StudentProfileServiceImpl implements StudentProfileService {
    
    private final StudentProfileRepository studentProfileRepository;
    private final UserAccountRepository userAccountRepository;

    // helper method to get the user account based on college email
    private UserAccount getUser(String collegeEmail) {

        return userAccountRepository.findByCollegeEmail(collegeEmail)
        .orElseThrow(() -> new RuntimeException("User not found"));
    }

    @Override
    public StudentProfileResponse getProfile(String collegeEmail) {
        
        UserAccount user = getUser(collegeEmail);

        StudentProfile profile = studentProfileRepository.findByUser(user)
                        .orElseThrow(() -> new RuntimeException("Profile not found"));
        
        return mapToResponse(profile);
    }

    private StudentProfileResponse mapToResponse(StudentProfile profile) {

        return StudentProfileResponse.builder()

                .collegeEmail(profile.getUser().getCollegeEmail())
                .usn(profile.getUsn())
                .fullName(profile.getFullName())
                .mobile(profile.getMobile())
                .alternateEmail(profile.getAlternateEmail())
                .dateOfBirth(profile.getDateOfBirth())
                .gender(profile.getGender())
                .department(profile.getDepartment())
                .branch(profile.getBranch())
                .currentSemester(profile.getCurrentSemester())
                .cgpa(profile.getCgpa())
                .activeBacklogs(profile.getActiveBacklogs())
                .address(profile.getAddress())
                .city(profile.getCity())
                .state(profile.getState())
                .country(profile.getCountry())
                .pincode(profile.getPincode())
                .linkedinUrl(profile.getLinkedinUrl())
                .githubUrl(profile.getGithubUrl())
                .portfolioUrl(profile.getPortfolioUrl())
                .resumeUrl(profile.getResumeUrl())
                .photoUrl(profile.getPhotoUrl())

                .build();

    }

    @Override
    public StudentProfileResponse updateProfile(
            String collegeEmail,
            StudentProfileRequest request) {

        UserAccount user = getUser(collegeEmail);

        StudentProfile profile = studentProfileRepository
                .findByUser(user)
                .orElse(
                        StudentProfile.builder()
                                .user(user)
                                .build());

        profile.setUsn(request.getUsn());
        profile.setFullName(request.getFullName());
        profile.setMobile(request.getMobile());
        profile.setAlternateEmail(request.getAlternateEmail());
        profile.setDateOfBirth(request.getDateOfBirth());
        profile.setGender(request.getGender());
        profile.setDepartment(request.getDepartment());
        profile.setBranch(request.getBranch());
        profile.setCurrentSemester(request.getCurrentSemester());
        profile.setCgpa(request.getCgpa());
        profile.setActiveBacklogs(request.getActiveBacklogs());
        profile.setAddress(request.getAddress());
        profile.setCity(request.getCity());
        profile.setState(request.getState());
        profile.setCountry(request.getCountry());
        profile.setPincode(request.getPincode());
        profile.setLinkedinUrl(request.getLinkedinUrl());
        profile.setGithubUrl(request.getGithubUrl());
        profile.setPortfolioUrl(request.getPortfolioUrl());

        StudentProfile saved =
                studentProfileRepository.save(profile);

        return mapToResponse(saved);
    }
}
