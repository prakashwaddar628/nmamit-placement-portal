package com.nmamit.placement_backend.dashboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentDashboardDTO {

    private boolean profileCompleted;
    private boolean resumeUploaded;
    private long applications;
    private long selected;
    private long interviews;
    private long upcomingDrives;
}
