package com.nmamit.placement_backend.common.mapper;

import com.nmamit.placement_backend.Resume.dto.response.ResumeResponse;
import com.nmamit.placement_backend.Resume.entity.Resume;
import org.springframework.stereotype.Component;

@Component
public class ResumeMapper {

    public ResumeResponse toResponse(Resume resume) {
        return ResumeResponse.builder()
                .id(resume.getId())
                .fileName(resume.getFileName())
                .fileUrl(resume.getFileUrl())
                .fileSize(resume.getFileSize())
                .uploadedAt(resume.getUploadedAt())
                .build();
    }
}
