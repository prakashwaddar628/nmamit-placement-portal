package com.nmamit.placement_backend.jobdrive.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.HashSet;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import com.nmamit.placement_backend.company.entity.Company;
import com.nmamit.placement_backend.enums.JobDriveStatus;
import com.nmamit.placement_backend.enums.JobType;

import jakarta.persistence.*;

import lombok.*;

@Entity
@Table(name = "job_drive")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JobDrive {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "company_id", nullable = false)
    private Company company;

    private String jobRole;

    @Enumerated(EnumType.STRING)
    private JobType jobType;

    private BigDecimal packageLpa;

    private String location;

    private LocalDate driveDate;

    private LocalDate registrationDeadline;

    private BigDecimal minimumCgpa;

    private Integer allowedBacklogs;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Enumerated(EnumType.STRING)
    @Builder.Default
    private JobDriveStatus status = JobDriveStatus.OPEN;

    @CreationTimestamp
    private LocalDateTime createdAt;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

}
