package com.nmamit.placement_backend.Resume.service;

import org.springframework.stereotype.Service;

import com.nmamit.placement_backend.Resume.dto.response.ResumeResponse;

import org.springframework.web.multipart.MultipartFile;

@Service
public interface ResumeService {
    
    ResumeResponse uploadResume(String collegeEmail, MultipartFile file);

    ResumeResponse getResume(String collegeEmail);

    void deleteResume(String collegeEmail);
}
