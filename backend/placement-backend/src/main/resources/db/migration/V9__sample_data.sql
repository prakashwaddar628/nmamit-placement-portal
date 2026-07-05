-- V9: Sample data for development and testing
-- Passwords are BCrypt hashed: 'Password@123'

-- Users (1 admin, 5 students)
INSERT INTO users (college_email, password, role, is_active, created_at, updated_at) VALUES
('admin@nmamit.in',     '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPZsjn3EFC6', 'ADMIN',   1, NOW(), NOW()),
('rahul.k@nmamit.in',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPZsjn3EFC6', 'STUDENT', 1, NOW(), NOW()),
('priya.s@nmamit.in',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPZsjn3EFC6', 'STUDENT', 1, NOW(), NOW()),
('arjun.m@nmamit.in',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPZsjn3EFC6', 'STUDENT', 1, NOW(), NOW()),
('sneha.p@nmamit.in',   '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPZsjn3EFC6', 'STUDENT', 1, NOW(), NOW()),
('vikram.n@nmamit.in',  '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LPZsjn3EFC6', 'STUDENT', 1, NOW(), NOW());

-- Student Profiles
INSERT INTO student_profile (user_id, usn, full_name, mobile, alternate_email, date_of_birth, gender, department, branch, current_semester, cgpa, active_backlogs, city, state, country, pincode, created_at, updated_at) VALUES
((SELECT id FROM users WHERE college_email='rahul.k@nmamit.in'),   '4NM21CS001', 'Rahul Kumar',   '9876543210', 'rahul.kumar@gmail.com', '2002-06-15', 'Male',   'Computer Science', 'CSE', 8, 8.75, 0, 'Mangaluru', 'Karnataka', 'India', '575001', NOW(), NOW()),
((SELECT id FROM users WHERE college_email='priya.s@nmamit.in'),   '4NM21CS002', 'Priya Sharma',  '9876543211', 'priya.sharma@gmail.com', '2002-03-22', 'Female', 'Computer Science', 'CSE', 8, 9.10, 0, 'Mangaluru', 'Karnataka', 'India', '575001', NOW(), NOW()),
((SELECT id FROM users WHERE college_email='arjun.m@nmamit.in'),   '4NM21EC001', 'Arjun Mehta',   '9876543212', 'arjun.mehta@gmail.com',  '2002-11-08', 'Male',   'Electronics',      'ECE', 8, 7.80, 1, 'Udupi',     'Karnataka', 'India', '576101', NOW(), NOW()),
((SELECT id FROM users WHERE college_email='sneha.p@nmamit.in'),   '4NM21IS001', 'Sneha Pai',     '9876543213', 'sneha.pai@gmail.com',    '2002-01-30', 'Female', 'Information Science', 'ISE', 8, 8.95, 0, 'Mangaluru', 'Karnataka', 'India', '575002', NOW(), NOW()),
((SELECT id FROM users WHERE college_email='vikram.n@nmamit.in'),  '4NM21ME001', 'Vikram Naik',   '9876543214', 'vikram.naik@gmail.com',  '2002-09-14', 'Male',   'Mechanical',       'ME',  8, 7.50, 2, 'Mangaluru', 'Karnataka', 'India', '575003', NOW(), NOW());

-- Companies
INSERT INTO company (company_name, website, industry, description, logo_url, active, created_at, updated_at) VALUES
('Infosys',         'https://www.infosys.com',     'IT Services',         'Global leader in next-generation digital services and consulting.',           NULL, 1, NOW(), NOW()),
('Wipro',           'https://www.wipro.com',       'IT Services',         'Leading technology services and consulting company.',                         NULL, 1, NOW(), NOW()),
('TCS',             'https://www.tcs.com',         'IT Services',         'Multinational information technology services and consulting company.',        NULL, 1, NOW(), NOW()),
('Cognizant',       'https://www.cognizant.com',   'IT Services',         'American multinational technology company.',                                  NULL, 1, NOW(), NOW()),
('Accenture',       'https://www.accenture.com',   'Consulting & Tech',   'Global professional services company offering digital, cloud, and security.', NULL, 1, NOW(), NOW());

-- Job Drives
INSERT INTO job_drive (company_id, job_role, job_type, package_lpa, location, drive_date, registration_deadline, minimum_cgpa, allowed_backlogs, description, status, created_at, updated_at) VALUES
((SELECT id FROM company WHERE company_name='Infosys'),   'Systems Engineer',     'FULL_TIME', 3.60, 'Bengaluru', '2026-08-15', '2026-08-10', 6.50, 0, 'Entry-level software engineering role.',         'OPEN',   NOW(), NOW()),
((SELECT id FROM company WHERE company_name='Wipro'),     'Project Engineer',     'FULL_TIME', 3.50, 'Hyderabad', '2026-08-20', '2026-08-15', 6.00, 1, 'Rotational program for fresh graduates.',         'OPEN',   NOW(), NOW()),
((SELECT id FROM company WHERE company_name='TCS'),       'Assistant Consultant', 'FULL_TIME', 3.36, 'Chennai',   '2026-08-25', '2026-08-20', 6.00, 0, 'TCS National Qualifier Test based hiring.',       'OPEN',   NOW(), NOW()),
((SELECT id FROM company WHERE company_name='Cognizant'), 'Programmer Analyst',   'FULL_TIME', 4.00, 'Pune',      '2026-09-01', '2026-08-28', 7.00, 0, 'GenC Next program for top performers.',           'OPEN',   NOW(), NOW()),
((SELECT id FROM company WHERE company_name='Accenture'), 'Associate Software',   'FULL_TIME', 4.50, 'Bengaluru', '2026-07-01', '2026-06-25', 7.00, 0, 'Digital transformation consulting role.',         'CLOSED', NOW(), NOW());

-- Eligible Branches for Job Drives
INSERT INTO job_drive_eligible_branch (job_drive_id, branch) VALUES
((SELECT id FROM job_drive WHERE job_role='Systems Engineer'),     'CSE'),
((SELECT id FROM job_drive WHERE job_role='Systems Engineer'),     'ISE'),
((SELECT id FROM job_drive WHERE job_role='Project Engineer'),     'CSE'),
((SELECT id FROM job_drive WHERE job_role='Project Engineer'),     'ECE'),
((SELECT id FROM job_drive WHERE job_role='Assistant Consultant'), 'CSE'),
((SELECT id FROM job_drive WHERE job_role='Assistant Consultant'), 'ISE'),
((SELECT id FROM job_drive WHERE job_role='Assistant Consultant'), 'ECE'),
((SELECT id FROM job_drive WHERE job_role='Programmer Analyst'),   'CSE'),
((SELECT id FROM job_drive WHERE job_role='Associate Software'),   'CSE'),
((SELECT id FROM job_drive WHERE job_role='Associate Software'),   'ISE');

-- Applications
INSERT INTO application (student_profile_id, job_drive_id, status, applied_at, updated_at) VALUES
((SELECT sp.id FROM student_profile sp JOIN users u ON sp.user_id=u.id WHERE u.college_email='rahul.k@nmamit.in'),
 (SELECT id FROM job_drive WHERE job_role='Systems Engineer'),     'APPLIED',     NOW(), NOW()),
((SELECT sp.id FROM student_profile sp JOIN users u ON sp.user_id=u.id WHERE u.college_email='rahul.k@nmamit.in'),
 (SELECT id FROM job_drive WHERE job_role='Programmer Analyst'),   'INTERVIEW',   NOW(), NOW()),
((SELECT sp.id FROM student_profile sp JOIN users u ON sp.user_id=u.id WHERE u.college_email='priya.s@nmamit.in'),
 (SELECT id FROM job_drive WHERE job_role='Systems Engineer'),     'SELECTED',    NOW(), NOW()),
((SELECT sp.id FROM student_profile sp JOIN users u ON sp.user_id=u.id WHERE u.college_email='priya.s@nmamit.in'),
 (SELECT id FROM job_drive WHERE job_role='Programmer Analyst'),   'APPLIED',     NOW(), NOW()),
((SELECT sp.id FROM student_profile sp JOIN users u ON sp.user_id=u.id WHERE u.college_email='sneha.p@nmamit.in'),
 (SELECT id FROM job_drive WHERE job_role='Systems Engineer'),     'SHORTLISTED', NOW(), NOW());
