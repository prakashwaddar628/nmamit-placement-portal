package com.nmamit.placement_backend.education.service.impl;

import com.nmamit.placement_backend.common.exception.BadRequestException;
import com.nmamit.placement_backend.common.exception.ResourceNotFoundException;
import com.nmamit.placement_backend.common.mapper.EducationMapper;
import com.nmamit.placement_backend.education.dto.request.EducationRequest;
import com.nmamit.placement_backend.education.dto.response.EducationResponse;
import com.nmamit.placement_backend.education.entity.Education;
import com.nmamit.placement_backend.education.repository.EducationRepository;
import com.nmamit.placement_backend.education.service.EducationService;
import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.repository.UserAccountRepository;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import com.nmamit.placement_backend.student.repository.StudentProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {

    private final EducationRepository educationRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserAccountRepository userAccountRepository;
    private final EducationMapper educationMapper;

    private UserAccount getUser(String collegeEmail) {
        return userAccountRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    private StudentProfile getStudentProfile(String collegeEmail) {
        UserAccount user = getUser(collegeEmail);
        return studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));
    }

    @Override
    public EducationResponse addEducation(String collegeEmail, EducationRequest request) {
        log.info("Adding education for: {}", collegeEmail);
        StudentProfile profile = getStudentProfile(collegeEmail);
        Education education = Education.builder()
                .studentProfile(profile)
                .educationType(request.getEducationType())
                .institutionName(request.getInstitutionName())
                .boardOrUniversity(request.getBoardOrUniversity())
                .specialization(request.getSpecialization())
                .percentage(request.getPercentage())
                .cgpa(request.getCgpa())
                .passingYear(request.getPassingYear())
                .build();
        Education saved = educationRepository.save(education);
        log.info("Education record created with id: {}", saved.getId());
        return educationMapper.toResponse(saved);
    }

    @Override
    public List<EducationResponse> getEducation(String collegeEmail) {
        log.debug("Fetching education for: {}", collegeEmail);
        StudentProfile profile = getStudentProfile(collegeEmail);
        return educationRepository.findByStudentProfile(profile)
                .stream()
                .map(educationMapper::toResponse)
                .toList();
    }

    @Override
    public EducationResponse updateEducation(String collegeEmail, Long educationId, EducationRequest request) {
        log.info("Updating education id: {} for: {}", educationId, collegeEmail);
        StudentProfile profile = getStudentProfile(collegeEmail);
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education record not found"));
        if (!education.getStudentProfile().getId().equals(profile.getId())) {
            throw new BadRequestException("You cannot update another student's education.");
        }
        education.setEducationType(request.getEducationType());
        education.setInstitutionName(request.getInstitutionName());
        education.setBoardOrUniversity(request.getBoardOrUniversity());
        education.setSpecialization(request.getSpecialization());
        education.setPercentage(request.getPercentage());
        education.setCgpa(request.getCgpa());
        education.setPassingYear(request.getPassingYear());
        return educationMapper.toResponse(educationRepository.save(education));
    }

    @Override
    public void deleteEducation(String collegeEmail, Long educationId) {
        log.info("Deleting education id: {} for: {}", educationId, collegeEmail);
        StudentProfile profile = getStudentProfile(collegeEmail);
        Education education = educationRepository.findById(educationId)
                .orElseThrow(() -> new ResourceNotFoundException("Education record not found"));
        if (!education.getStudentProfile().getId().equals(profile.getId())) {
            throw new BadRequestException("You cannot delete another student's education.");
        }
        educationRepository.delete(education);
    }
}
