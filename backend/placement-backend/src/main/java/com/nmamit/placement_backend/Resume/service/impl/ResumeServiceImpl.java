package com.nmamit.placement_backend.Resume.service.impl;

import org.springframework.stereotype.Service;

import com.nmamit.placement_backend.repository.UserAccountRepository;
import com.nmamit.placement_backend.student.repository.StudentProfileRepository;
import com.nmamit.placement_backend.Resume.repository.ResumeRepository;
import com.nmamit.placement_backend.Resume.service.ResumeService;
import com.nmamit.placement_backend.common.exception.*;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import com.nmamit.placement_backend.Resume.entity.Resume;
import com.nmamit.placement_backend.Resume.dto.response.ResumeResponse;
import com.nmamit.placement_backend.entity.UserAccount;

import lombok.*;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;


import org.springframework.web.multipart.MultipartFile;
import org.springframework.beans.factory.annotation.Value;

@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserAccountRepository userAccountRepository;

    @Value("${app.resume.upload-dir}")
    private String uploadDir;

    private StudentProfile getStudentProfile(String collegeEmail) {

        UserAccount user = userAccountRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        return studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));
    }

    private ResumeResponse mapToResponse(Resume resume) {

        return ResumeResponse.builder()
                .id(resume.getId())
                .fileName(resume.getFileName())
                .fileUrl(resume.getFileUrl())
                .fileSize(resume.getFileSize())
                .uploadedAt(resume.getUploadedAt())
                .build();
    }

    @Override
    public ResumeResponse uploadResume(String collegeEmail, MultipartFile file) {

        StudentProfile student = getStudentProfile(collegeEmail);

        if (file.isEmpty()) {
            throw new BadRequestException("Please select a file.");
        }

        if (!"application/pdf".equals(file.getContentType())) {
            throw new BadRequestException("Only PDF files are allowed.");
        }

        if (file.getSize() > 5 * 1024 * 1024) {
            throw new BadRequestException("Maximum file size is 5 MB.");
        }

        String fileName = UUID.randomUUID() + "_" + file.getOriginalFilename();

        Path uploadPath = Paths.get(uploadDir);

        try {

            Files.createDirectories(uploadPath);

            Resume existingResume =
                    resumeRepository.findByStudentProfile(student).orElse(null);

            if (existingResume != null) {

                Path oldFile = uploadPath.resolve(existingResume.getFileName());

                Files.deleteIfExists(oldFile);
            }

            Files.copy(
                    file.getInputStream(),
                    uploadPath.resolve(fileName),
                    StandardCopyOption.REPLACE_EXISTING
            );

            Resume resume = existingResume != null
                    ? existingResume
                    : new Resume();

            resume.setStudentProfile(student);
            resume.setFileName(fileName);
            resume.setFileUrl("/uploads/resumes/" + fileName);
            resume.setFileSize(file.getSize());

            Resume saved = resumeRepository.save(resume);

            return mapToResponse(saved);

        } catch (IOException e) {
            throw new RuntimeException("Failed to upload resume.", e);
        }
    }

    @Override
    public ResumeResponse getResume(String collegeEmail) {

        StudentProfile student = getStudentProfile(collegeEmail);

        Resume resume = resumeRepository.findByStudentProfile(student)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

        return mapToResponse(resume);
    }

    @Override
    public void deleteResume(String collegeEmail) {

        StudentProfile student = getStudentProfile(collegeEmail);

        Resume resume = resumeRepository.findByStudentProfile(student)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));

        try {

            Path uploadPath =
                    Paths.get(uploadDir);

            Files.deleteIfExists(uploadPath.resolve(resume.getFileName()));

            resumeRepository.delete(resume);

        } catch (IOException e) {
            throw new RuntimeException("Failed to delete resume.", e);
        }
    }
}