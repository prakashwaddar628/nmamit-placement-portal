
## Backend Technology Stack

| Technology      | Version            |
| --------------- | ------------------ |
| Java            | 21 (LTS)           |
| Spring Boot     | 3.5.x              |
| Maven           | Latest             |
| MySQL           | 8.x                |
| Spring Security | JWT                |
| Spring Data JPA | Latest             |
| Validation      | Jakarta Validation |
| Lombok          | Latest             |
| MapStruct       | Optional (later)   |
| Swagger/OpenAPI | springdoc-openapi  |
| Logging         | SLF4J + Logback    |

🏗 Architecture

We'll use Clean Architecture instead of putting everything in one package.

backend/

src/main/java/com/nmamit/placementportal/

├── config/
├── controller/
├── dto/
│
├── entity/
│
├── repository/
│
├── service/
│
├── service/impl/
│
├── security/
│
├── exception/
│
├── util/
│
├── validation/
│
└── PlacementPortalApplication.java

This is much cleaner than the usual MVC structure used in many student projects.

👥 Roles

Only two roles initially.

ROLE_ADMIN

ROLE_STUDENT

Simple and sufficient.

📊 Database Design

We won't create tables randomly.

These will be our tables:

users

student_profiles

education_details

companies

job_drives

applications

placement_team

notifications
🔐 Authentication Flow

Student

Register

↓

Login

↓

JWT Token

↓

Access APIs

Admin

Default Admin

↓

Login

↓

JWT Token
📌 APIs
Authentication
POST /api/auth/register

POST /api/auth/login

POST /api/auth/refresh

GET /api/auth/me
Student
GET /students/profile

PUT /students/profile

POST /students/photo

POST /students/resume
Companies
GET /companies

GET /companies/{id}

GET /companies/upcoming

GET /companies/previous
Applications
POST /applications/apply

GET /applications/my

GET /applications/status/{id}
Admin
POST /admin/company

PUT /admin/company/{id}

DELETE /admin/company/{id}

GET /admin/students

GET /admin/applications

GET /admin/dashboard
📁 File Upload

We'll store files locally.

backend/

uploads/

photos/

resumes/

jd/

No cloud required.

🔒 Security

We'll implement:

JWT Authentication
BCrypt Password Encoding
Role-Based Authorization
Request Validation
Global Exception Handling
📈 Development Plan
Phase 1
✅ Spring Boot Project Setup
✅ MySQL Connection
✅ JWT Authentication
✅ User Entity
Phase 2
    Student Profile
Phase 3
    Company Management
Phase 4
    Applications
Phase 5
    Admin Dashboard
Phase 6
    Swagger Documentation

## 🚀 Before We Write Code

``` 
    I want to define the database schema properly because changing it later is painful.

    These are the entities I propose:

    Entity	Purpose
    User	Login credentials and role (Admin/Student)
    StudentProfile	Personal details, contact information, address
    EducationDetail	SSLC, PUC/Diploma, UG, PG records
    Company	Company information (name, logo, website, description)
    JobDrive	Placement drive details (role, CTC, eligibility, deadlines, JD file)
    Application	Student applications and status
    PlacementTeam	Placement officer profiles shown in the app
    Notification	Announcements and drive notifications

    This separation avoids duplicated data and keeps the design normalized.

    I also have one important suggestion before we start:

    Instead of using plain uploads/, let's organize uploaded files by type and student. For example:

    uploads/
    ├── resumes/
    │   ├── 4NM21CS001_resume.pdf
    │   └── 4NM21CS002_resume.pdf
    ├── photos/
    │   ├── 4NM21CS001_photo.jpg
    │   └── 4NM21CS002_photo.jpg
    └── job-descriptions/
        ├── amazon_sde_2026.pdf
        └── tcs_ninja_2026.pdf

    This makes file management much easier during development and demonstrations.
```