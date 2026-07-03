-- ============================================================
-- V3 : Create education_details table
-- Links to student_profile (one student → many education records)
-- Education types: SSLC, PUC, DIPLOMA, UG, PG
-- ============================================================

CREATE TABLE education_details (

    id                  BIGINT          NOT NULL AUTO_INCREMENT,

    -- FK to student_profile; cascade deletes keep referential integrity
    student_id          BIGINT          NOT NULL,

    -- One of: SSLC | PUC | DIPLOMA | UG | PG
    education_type      ENUM('SSLC', 'PUC', 'DIPLOMA', 'UG', 'PG') NOT NULL,

    institution_name    VARCHAR(200)    NOT NULL,

    -- Board (for school) or University (for college)
    board_or_university VARCHAR(150)    DEFAULT NULL,

    -- Branch / stream / major (e.g. "Computer Science", "Science – PCM")
    specialization      VARCHAR(150)    DEFAULT NULL,

    -- Percentage obtained (e.g. 92.50). Mutually exclusive with cgpa in practice,
    -- but both are stored to support different institutions' grading systems.
    percentage          DECIMAL(5, 2)   DEFAULT NULL,

    -- CGPA on a 10-point scale (e.g. 8.75)
    cgpa                DECIMAL(4, 2)   DEFAULT NULL,

    -- Year the course / exam was completed (e.g. 2021)
    passing_year        YEAR            DEFAULT NULL,

    -- Audit columns (mirrors student_profile pattern)
    created_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP       NOT NULL DEFAULT CURRENT_TIMESTAMP
                            ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (id),

    CONSTRAINT fk_education_student
        FOREIGN KEY (student_id)
        REFERENCES student_profile (id)
        ON DELETE CASCADE

) ENGINE = InnoDB
  DEFAULT CHARSET = utf8mb4
  COLLATE = utf8mb4_unicode_ci;