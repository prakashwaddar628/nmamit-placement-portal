CREATE TABLE job_drive (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    company_id BIGINT NOT NULL,

    job_role VARCHAR(150) NOT NULL,

    job_type VARCHAR(50) NOT NULL,

    package_lpa DECIMAL(5,2),

    location VARCHAR(100),

    drive_date DATE,

    registration_deadline DATE,

    minimum_cgpa DECIMAL(4,2),

    allowed_backlogs INT,

    description TEXT,

    status VARCHAR(30),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_job_drive_company
        FOREIGN KEY (company_id)
        REFERENCES company(id)
);