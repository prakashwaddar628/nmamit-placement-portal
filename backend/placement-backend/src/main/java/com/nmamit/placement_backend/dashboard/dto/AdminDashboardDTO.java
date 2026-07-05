package com.nmamit.placement_backend.dashboard.dto;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminDashboardDTO {

    private long students;
    private long companies;
    private long jobDrives;
    private long applications;
    private long selectedStudents;
    private long openDrives;
    private long closedDrives;
}
