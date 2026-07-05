package com.nmamit.placement_backend.Resume.dto.response;

import java.time.LocalDateTime;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ResumeResponse {
    
    private Long id;
    private String fileName;
    private String fileUrl;
    private Long fileSize;
    private LocalDateTime uploadedAt;
    
}
