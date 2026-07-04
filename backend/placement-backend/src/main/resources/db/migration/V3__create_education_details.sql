CREATE TABLE education_details (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    student_profile_id BIGINT NOT NULL,

    education_type VARCHAR(20) NOT NULL,

    institution_name VARCHAR(200) NOT NULL,

    board_or_university VARCHAR(150),

    specialization VARCHAR(150),

    percentage DECIMAL(5,2),

    cgpa DECIMAL(4,2),

    passing_year INT,

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_education_student
        FOREIGN KEY (student_profile_id)
        REFERENCES student_profile(id)
        ON DELETE CASCADE
);