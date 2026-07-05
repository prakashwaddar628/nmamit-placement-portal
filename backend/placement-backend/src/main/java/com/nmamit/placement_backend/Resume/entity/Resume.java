package com.nmamit.placement_backend.Resume.entity;

import lombok.*;

import java.time.LocalDateTime;

import org.hibernate.annotations.CreationTimestamp;

import com.nmamit.placement_backend.student.entity.StudentProfile;

import jakarta.persistence.*;

@Entity
@Table(name = "resume")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Resume {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "student_profile_id", nullable = false)
    private StudentProfile studentProfile;

    @Column(nullable = false)
    private String fileName;

    @Column(nullable = false)
    private String fileUrl;

    @Column(nullable = false)
    private Long fileSize;

    @CreationTimestamp
    private LocalDateTime uploadedAt;

}
