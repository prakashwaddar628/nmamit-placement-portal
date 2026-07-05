package com.nmamit.placement_backend.common.mapper;

import com.nmamit.placement_backend.student.dto.response.StudentProfileResponse;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import org.springframework.stereotype.Component;

@Component
public class StudentMapper {

    public StudentProfileResponse toResponse(StudentProfile profile) {
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
}
