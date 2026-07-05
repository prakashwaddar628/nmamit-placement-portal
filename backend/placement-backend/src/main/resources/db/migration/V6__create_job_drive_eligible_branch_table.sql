CREATE TABLE job_drive_eligible_branch (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    job_drive_id BIGINT NOT NULL,

    branch VARCHAR(100) NOT NULL,

    CONSTRAINT fk_job_drive_branch
        FOREIGN KEY(job_drive_id)
        REFERENCES job_drive(id)
        ON DELETE CASCADE
);