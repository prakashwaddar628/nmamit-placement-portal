package com.nmamit.placement_backend.Resume.service.impl;

import com.nmamit.placement_backend.Resume.dto.response.ResumeResponse;
import com.nmamit.placement_backend.Resume.entity.Resume;
import com.nmamit.placement_backend.Resume.repository.ResumeRepository;
import com.nmamit.placement_backend.Resume.service.ResumeService;
import com.nmamit.placement_backend.common.exception.BadRequestException;
import com.nmamit.placement_backend.common.exception.ResourceNotFoundException;
import com.nmamit.placement_backend.common.mapper.ResumeMapper;
import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.repository.UserAccountRepository;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import com.nmamit.placement_backend.student.repository.StudentProfileRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.*;
import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ResumeServiceImpl implements ResumeService {

    private final ResumeRepository resumeRepository;
    private final StudentProfileRepository studentProfileRepository;
    private final UserAccountRepository userAccountRepository;
    private final ResumeMapper resumeMapper;

    @Value("${app.resume.upload-dir}")
    private String uploadDir;

    private StudentProfile getStudentProfile(String collegeEmail) {
        UserAccount user = userAccountRepository.findByCollegeEmail(collegeEmail)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
        return studentProfileRepository.findByUser(user)
                .orElseThrow(() -> new ResourceNotFoundException("Student profile not found"));
    }

    @Override
    public ResumeResponse uploadResume(String collegeEmail, MultipartFile file) {
        log.info("Uploading resume for: {}", collegeEmail);
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
            Resume existingResume = resumeRepository.findByStudentProfile(student).orElse(null);
            if (existingResume != null) {
                Files.deleteIfExists(uploadPath.resolve(existingResume.getFileName()));
                log.debug("Old resume deleted: {}", existingResume.getFileName());
            }
            Files.copy(file.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            Resume resume = existingResume != null ? existingResume : new Resume();
            resume.setStudentProfile(student);
            resume.setFileName(fileName);
            resume.setFileUrl("/uploads/resumes/" + fileName);
            resume.setFileSize(file.getSize());

            Resume saved = resumeRepository.save(resume);
            log.info("Resume uploaded successfully for: {}", collegeEmail);
            return resumeMapper.toResponse(saved);
        } catch (IOException e) {
            log.error("Failed to upload resume for: {}", collegeEmail, e);
            throw new RuntimeException("Failed to upload resume.", e);
        }
    }

    @Override
    public ResumeResponse getResume(String collegeEmail) {
        log.debug("Fetching resume for: {}", collegeEmail);
        StudentProfile student = getStudentProfile(collegeEmail);
        Resume resume = resumeRepository.findByStudentProfile(student)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
        return resumeMapper.toResponse(resume);
    }

    @Override
    public void deleteResume(String collegeEmail) {
        log.info("Deleting resume for: {}", collegeEmail);
        StudentProfile student = getStudentProfile(collegeEmail);
        Resume resume = resumeRepository.findByStudentProfile(student)
                .orElseThrow(() -> new ResourceNotFoundException("Resume not found"));
        try {
            Files.deleteIfExists(Paths.get(uploadDir).resolve(resume.getFileName()));
            resumeRepository.delete(resume);
            log.info("Resume deleted for: {}", collegeEmail);
        } catch (IOException e) {
            log.error("Failed to delete resume for: {}", collegeEmail, e);
            throw new RuntimeException("Failed to delete resume.", e);
        }
    }
}