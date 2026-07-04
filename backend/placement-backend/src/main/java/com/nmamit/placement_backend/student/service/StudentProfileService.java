package com.nmamit.placement_backend.student.service;

import com.nmamit.placement_backend.student.dto.request.StudentProfileRequest;
import com.nmamit.placement_backend.student.dto.response.StudentProfileResponse;

public interface StudentProfileService {

    StudentProfileResponse getProfile(String collegeEmail);

    StudentProfileResponse updateProfile(String collegeEmail, StudentProfileRequest request);

}
