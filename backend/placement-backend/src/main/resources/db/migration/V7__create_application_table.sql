CREATE TABLE application (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    student_profile_id BIGINT NOT NULL,

    job_drive_id BIGINT NOT NULL,

    status VARCHAR(30) NOT NULL,

    applied_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_application_student
        FOREIGN KEY(student_profile_id)
        REFERENCES student_profile(id),

    CONSTRAINT fk_application_jobdrive
        FOREIGN KEY(job_drive_id)
        REFERENCES job_drive(id),
    
    UNIQUE(student_profile_id, job_drive_id)
);