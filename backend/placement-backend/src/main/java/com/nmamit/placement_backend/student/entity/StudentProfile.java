package com.nmamit.placement_backend.student.entity;
import com.nmamit.placement_backend.entity.UserAccount;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
@Table(name = "student_profile")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class StudentProfile {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, unique = true)
    private UserAccount user;

    private String usn;

    private String fullName;

    private String mobile;

    private String alternateEmail;

    private LocalDate dateOfBirth;

    private String gender;

    private String department;

    private String branch;

    private Integer currentSemester;

    private BigDecimal cgpa;

    private Integer activeBacklogs;

    private String address;

    private String city;

    private String state;

    private String country;

    private String pincode;

    private String linkedinUrl;

    private String githubUrl;

    private String portfolioUrl;

    private String resumeUrl;

    private String photoUrl;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;
}
