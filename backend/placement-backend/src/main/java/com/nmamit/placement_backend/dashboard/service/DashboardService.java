package com.nmamit.placement_backend.dashboard.service;

import com.nmamit.placement_backend.dashboard.dto.AdminDashboardDTO;
import com.nmamit.placement_backend.dashboard.dto.StudentDashboardDTO;

public interface DashboardService {

    StudentDashboardDTO getStudentDashboard(String collegeEmail);

    AdminDashboardDTO getAdminDashboard();
}
