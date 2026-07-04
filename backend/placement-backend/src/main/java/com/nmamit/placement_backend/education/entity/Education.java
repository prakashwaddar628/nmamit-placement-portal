package com.nmamit.placement_backend.education.entity;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.nmamit.placement_backend.enums.EducationType;
import com.nmamit.placement_backend.student.entity.StudentProfile;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "education_details")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Education {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_profile_id")
    private StudentProfile studentProfile;

    @Enumerated(EnumType.STRING)
    private EducationType educationType;

    private String institutionName;

    private String boardOrUniversity;

    private String specialization;

    private BigDecimal percentage;

    private BigDecimal cgpa;

    private Integer passingYear;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
