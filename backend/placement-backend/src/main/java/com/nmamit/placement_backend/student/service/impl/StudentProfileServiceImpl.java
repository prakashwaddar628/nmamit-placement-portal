package com.nmamit.placement_backend.student.service.impl;

import com.nmamit.placement_backend.common.exception.ResourceNotFoundException;
import com.nmamit.placement_backend.common.mapper.StudentMapper;
import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.repository.UserAccountRepository;
import com.nmamit.placement_backend.student.dto.request.StudentProfileRequest;
import com.nmamit.placement_backend.student.dto.response.StudentProfileResponse;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import com.nmamit.placement_backend.student.repository.StudentProfileRepository;
import com.nmamit.placement_backend.student.service.StudentProfileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class StudentProfileServiceImpl implements StudentProfileService {

    private final StudentProfileRepository studentProfileRepository;
    private final UserAccountRepository userAccountRepository;
    private final StudentMapper studentMapper;

    private UserAccount getUser(String collegeEmail) {
        return userAccountRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    @Override
    public StudentProfileResponse getProfile(String collegeEmail) {
        log.info("Fetching profile for: {}", collegeEmail);
        UserAccount user = getUser(collegeEmail);
        StudentProfile profile = studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Profile not found"));
        return studentMapper.toResponse(profile);
    }

    @Override
    public StudentProfileResponse updateProfile(String collegeEmail, StudentProfileRequest request) {
        log.info("Updating profile for: {}", collegeEmail);
        UserAccount user = getUser(collegeEmail);

        StudentProfile profile = studentProfileRepository
                .findByUser(user)
                .orElse(StudentProfile.builder().user(user).build());

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

        StudentProfile saved = studentProfileRepository.save(profile);
        log.info("Profile updated for: {}", collegeEmail);
        return studentMapper.toResponse(saved);
    }
}
