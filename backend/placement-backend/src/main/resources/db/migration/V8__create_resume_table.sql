CREATE TABLE resume (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    student_profile_id BIGINT NOT NULL UNIQUE,

    file_name VARCHAR(255) NOT NULL,

    file_url VARCHAR(500) NOT NULL,

    file_size BIGINT NOT NULL,

    uploaded_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    CONSTRAINT fk_resume_student
        FOREIGN KEY (student_profile_id)
        REFERENCES student_profile(id)
);