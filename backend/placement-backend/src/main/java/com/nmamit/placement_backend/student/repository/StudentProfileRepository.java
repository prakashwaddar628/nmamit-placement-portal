package com.nmamit.placement_backend.student.repository;

import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.student.entity.StudentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StudentProfileRepository extends JpaRepository<StudentProfile, Long> {

    Optional<StudentProfile> findByUser(UserAccount user);
}
