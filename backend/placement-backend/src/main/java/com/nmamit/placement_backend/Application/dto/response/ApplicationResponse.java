package com.nmamit.placement_backend.Application.dto.response;

import com.nmamit.placement_backend.enums.ApplicationStatus;

import java.time.LocalDateTime;


import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationResponse {
    
    private Long id;

    private String companyName;

    private String jobRole;

    private ApplicationStatus status;

    private LocalDateTime appliedAt;

}
