CREATE TABLE student_profile (

    id BIGINT AUTO_INCREMENT PRIMARY KEY,

    user_id BIGINT NOT NULL UNIQUE,

    usn VARCHAR(20) NOT NULL UNIQUE,

    full_name VARCHAR(150) NOT NULL,

    mobile VARCHAR(15),

    alternate_email VARCHAR(100),

    date_of_birth DATE,

    gender VARCHAR(20),

    department VARCHAR(100),

    branch VARCHAR(100),

    current_semester INT,

    cgpa DECIMAL(4,2),

    active_backlogs INT DEFAULT 0,

    address TEXT,

    city VARCHAR(100),

    state VARCHAR(100),

    country VARCHAR(100),

    pincode VARCHAR(10),

    linkedin_url VARCHAR(255),

    github_url VARCHAR(255),

    portfolio_url VARCHAR(255),

    resume_url VARCHAR(255),

    photo_url VARCHAR(255),

    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
        ON UPDATE CURRENT_TIMESTAMP,

    CONSTRAINT fk_student_profile_user
        FOREIGN KEY (user_id)
        REFERENCES users(id)
        ON DELETE CASCADE
);