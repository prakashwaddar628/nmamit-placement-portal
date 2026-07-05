package com.nmamit.placement_backend.Resume.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

import com.nmamit.placement_backend.student.entity.StudentProfile;
import com.nmamit.placement_backend.Resume.entity.Resume;

@Repository
public interface ResumeRepository extends JpaRepository<Resume, Long> {
    
    Optional<Resume> findByStudentProfile(StudentProfile studentProfile);

    boolean existsByStudentProfile(StudentProfile studentProfile);

}
