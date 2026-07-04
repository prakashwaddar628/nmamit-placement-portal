package com.nmamit.placement_backend.education.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.nmamit.placement_backend.education.entity.Education;
import com.nmamit.placement_backend.student.entity.StudentProfile;

public interface EducationRepository extends JpaRepository<Education, Long>{
    
    List<Education> findByStudentProfile(StudentProfile studentProfile);

}
