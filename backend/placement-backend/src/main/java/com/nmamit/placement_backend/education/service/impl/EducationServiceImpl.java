package com.nmamit.placement_backend.education.service.impl;

import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.repository.UserAccountRepository;
import com.nmamit.placement_backend.education.dto.request.EducationRequest;
import com.nmamit.placement_backend.education.dto.response.EducationResponse;
import com.nmamit.placement_backend.education.entity.Education;
import com.nmamit.placement_backend.education.repository.EducationRepository;
import com.nmamit.placement_backend.education.service.EducationService;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import com.nmamit.placement_backend.student.repository.StudentProfileRepository;
import com.nmamit.placement_backend.common.exception.*;

import org.springframework.stereotype.Service;

import java.util.List;

import lombok.*;

@Service
@RequiredArgsConstructor
public class EducationServiceImpl implements EducationService {
    
    private final EducationRepository educationRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserAccountRepository userAccountRepository;

    // getUser
    private UserAccount getUser(String collegeEmail){
        return userAccountRepository.findByCollegeEmail(collegeEmail)
               .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    // getStudentProfile
    private StudentProfile getStudentProfile(String collegeEmail) {

        UserAccount user = getUser(collegeEmail);

        return studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));
    }

    // maptoResponse
    private EducationResponse mapToResponse(Education education) {

        return EducationResponse.builder()
                .id(education.getId())
                .educationType(education.getEducationType())
                .institutionName(education.getInstitutionName())
                .boardOrUniversity(education.getBoardOrUniversity())
                .specialization(education.getSpecialization())
                .percentage(education.getPercentage())
                .cgpa(education.getCgpa())
                .passingYear(education.getPassingYear())
                .build();
    }

    @Override
    public EducationResponse addEducation(
            String collegeEmail,
            EducationRequest request) {

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

        return mapToResponse(saved);
    }

    @Override
    public List<EducationResponse> getEducation(
            String collegeEmail) {

        StudentProfile profile = getStudentProfile(collegeEmail);

        return educationRepository.findByStudentProfile(profile)
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public EducationResponse updateEducation(
            String collegeEmail,
            Long educationId,
            EducationRequest request) {

        StudentProfile profile = getStudentProfile(collegeEmail);

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Education record not found"));

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

        Education updated = educationRepository.save(education);

        return mapToResponse(updated);
    }

    @Override
    public void deleteEducation(
            String collegeEmail,
            Long educationId) {

        StudentProfile profile = getStudentProfile(collegeEmail);

        Education education = educationRepository.findById(educationId)
                .orElseThrow(() ->
                        new ResourceNotFoundException("Education record not found"));

        if (!education.getStudentProfile().getId().equals(profile.getId())) {
            throw new BadRequestException("You cannot delete another student's education.");
        }

        educationRepository.delete(education);
    }

}

