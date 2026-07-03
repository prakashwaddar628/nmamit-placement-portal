# NMAMIT Placement Portal

<div align="center">

**A full-stack campus recruitment management system for NMAM Institute of Technology, Nitte.**

[![Java](https://img.shields.io/badge/Java-21%20LTS-orange?logo=openjdk)](https://adoptium.net/)
[![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.5.x-6DB33F?logo=springboot)](https://spring.io/projects/spring-boot)
[![MySQL](https://img.shields.io/badge/MySQL-8.x-4479A1?logo=mysql)](https://www.mysql.com/)
[![Flutter](https://img.shields.io/badge/Flutter-Cross--Platform-02569B?logo=flutter)](https://flutter.dev/)
[![License: MIT](https://img.shields.io/badge/License-MIT-yellow.svg)](./LICENSE)

</div>

---

## 📋 Table of Contents

- [Overview](#overview)
- [Monorepo Structure](#monorepo-structure)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Database Schema](#database-schema)
- [Getting Started](#getting-started)
- [API Reference](#api-reference)
- [Environment Variables](#environment-variables)
- [Development Roadmap](#development-roadmap)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

The NMAMIT Placement Portal digitises the end-to-end campus placement process — from student registration and profile management to company drive announcements, application tracking, and admin dashboards.

| Module | Description |
|--------|-------------|
| **Backend** | Spring Boot 3 REST API — JWT auth, MySQL, Flyway migrations |
| **Flutter** | Cross-platform mobile & web frontend (under development) |
| **Docs** | Architecture decisions, ERD, API contracts |

---

## Monorepo Structure

```
nmamit-placement-portal/
├── backend/
│   └── placement-backend/              # Spring Boot application
│       ├── src/
│       │   ├── main/
│       │   │   ├── java/com/nmamit/placement_backend/
│       │   │   │   ├── common/         # Shared ApiResponse wrapper
│       │   │   │   ├── config/         # ApplicationConfig (auth beans)
│       │   │   │   ├── controller/     # REST controllers
│       │   │   │   ├── dto/
│       │   │   │   │   ├── request/    # LoginRequest, RegisterRequest …
│       │   │   │   │   └── response/   # LoginResponse, RegisterResponse …
│       │   │   │   ├── entity/         # JPA entities (UserAccount …)
│       │   │   │   ├── enums/          # Role (ADMIN, STUDENT)
│       │   │   │   ├── exception/      # Global exception handling
│       │   │   │   ├── repository/     # Spring Data JPA repositories
│       │   │   │   ├── security/       # JWT filter, JwtService, SecurityConfig
│       │   │   │   ├── service/        # Interfaces + impl/
│       │   │   │   ├── util/           # Utility helpers
│       │   │   │   └── validation/     # Custom Bean Validators
│       │   │   └── resources/
│       │   │       ├── db/migration/   # Flyway SQL scripts (V1 … Vn)
│       │   │       ├── application.properties
│       │   │       ├── application-dev.properties
│       │   │       └── application-prod.properties
│       │   └── test/
│       ├── uploads/                    # Local file storage (git-ignored)
│       │   ├── resumes/
│       │   ├── photos/
│       │   └── job-descriptions/
│       └── pom.xml
├── flutter/                            # Flutter app (🚧 in progress)
├── documentation/                      # Design docs & ERDs
├── .gitignore
├── LICENSE
└── README.md
```

---

## Features

### 👨‍🎓 Student
- Secure registration & login with JWT Bearer tokens
- Profile management — personal info, photo, resume upload
- Browse upcoming and past company drives
- Apply for placement drives with one click
- Track application status in real time
- Receive notifications and announcements

### 🏢 Admin (Placement Officer)
- Full CRUD for companies and job drives
- View and filter all student profiles and applications
- Placement dashboard with key statistics
- File management — job descriptions, student resumes & photos
- Manage placement team profiles
- Send announcements and push notifications

---

## Tech Stack

### Backend

| Technology | Version / Details |
|---|---|
| Java | 21 LTS |
| Spring Boot | 3.5.x |
| Spring Security | JWT (Stateless / Bearer) |
| Spring Data JPA | Latest |
| Flyway | DB migrations |
| MySQL | 8.x |
| Maven | 3.9+ |
| Jakarta Validation | Bean Validation 3 |
| Lombok | Latest |
| springdoc-openapi | 2.x (Swagger UI) |
| SLF4J + Logback | Structured logging |

### Frontend

| Technology | Details |
|---|---|
| Flutter | Dart / Cross-platform |
| HTTP client | dio / http package |

---

## Database Schema

Managed by **Flyway** — migrations live in `src/main/resources/db/migration/`.

| Migration | Table | Description |
|---|---|---|
| V1 | `users` | Auth credentials + role (ADMIN / STUDENT) |
| V2 | `student_profile` | Personal, academic & social details |
| V3 | `education_details` | Per-level academic history (SSLC→PG) |
| V4 *(planned)* | `companies` | Company master data |
| V5 *(planned)* | `job_drives` | Drive details, eligibility, CTC, JD file |
| V6 *(planned)* | `applications` | Student–drive join with status tracking |
| V7 *(planned)* | `placement_team` | Placement officer profiles |
| V8 *(planned)* | `notifications` | Announcements and drive alerts |

### Key Relationships

```
users ──────────────── student_profile  (1 : 1)
student_profile ─────── education_details  (1 : many)
student_profile ─────── applications  (1 : many)
job_drives ──────────── applications  (1 : many)
companies ───────────── job_drives  (1 : many)
```

---

## Getting Started

### Prerequisites

| Tool | Version | Download |
|---|---|---|
| Java (JDK) | 21 LTS | [Adoptium](https://adoptium.net/) |
| Maven | 3.9+ | [apache.org](https://maven.apache.org/download.cgi) |
| MySQL | 8.x | [mysql.com](https://dev.mysql.com/downloads/) |
| Flutter SDK | Latest stable | [flutter.dev](https://docs.flutter.dev/get-started/install) |
| Git | Any | [git-scm.com](https://git-scm.com/) |

---

### Backend Setup

```bash
# 1. Clone the repo
git clone https://github.com/prakashwaddar628/nmamit-placement-portal.git
cd nmamit-placement-portal/backend/placement-backend

# 2. Create the MySQL database
mysql -u root -p -e "CREATE DATABASE nmamit_placement_portal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# 3. Copy and configure the properties file
cp src/main/resources/application-dev.properties.example \
   src/main/resources/application-dev.properties
# Edit DB credentials, JWT secret, etc.

# 4. Run the application (Flyway runs migrations automatically)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev

# Windows
mvnw.cmd spring-boot:run -Dspring-boot.run.profiles=dev
```

The server starts on **`http://localhost:8080`**.

Swagger UI: **`http://localhost:8080/swagger-ui/index.html`**

---

### Flutter Setup

> 🚧 Flutter app is under active development.

```bash
cd flutter/
flutter pub get
flutter run
```

---

## API Reference

Full interactive docs available at `/swagger-ui/index.html` when the backend is running.

### Authentication (`/api/auth`)

| Method | Endpoint | Auth | Description |
|---|---|---|---|
| `POST` | `/api/auth/register` | Public | Register new student account |
| `POST` | `/api/auth/login` | Public | Login — returns JWT access token |
| `POST` | `/api/auth/refresh` | Bearer | Refresh access token |
| `GET` | `/api/auth/me` | Bearer | Get current authenticated user |

**Sample Register Request**
```json
{
  "collegeEmail": "4nm21cs001@nmamit.in",
  "password": "SecureP@ss123",
  "role": "STUDENT"
}
```

**Sample Login Response**
```json
{
  "accessToken": "eyJhbGci...",
  "tokenType": "Bearer",
  "expiresIn": 3600
}
```

---

### Student (`/students`) — Requires `ROLE_STUDENT`

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/students/profile` | Get own student profile |
| `PUT` | `/students/profile` | Update own student profile |
| `POST` | `/students/photo` | Upload profile photo (JPEG/PNG) |
| `POST` | `/students/resume` | Upload resume (PDF) |

---

### Companies (`/companies`) — Public / Student

| Method | Endpoint | Description |
|---|---|---|
| `GET` | `/companies` | List all companies |
| `GET` | `/companies/{id}` | Get company details |
| `GET` | `/companies/upcoming` | Upcoming placement drives |
| `GET` | `/companies/previous` | Past placement drives |

---

### Applications (`/applications`) — Requires `ROLE_STUDENT`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/applications/apply` | Apply for a drive |
| `GET` | `/applications/my` | My applications |
| `GET` | `/applications/status/{id}` | Application status by ID |

---

### Admin (`/admin`) — Requires `ROLE_ADMIN`

| Method | Endpoint | Description |
|---|---|---|
| `POST` | `/admin/company` | Create a company |
| `PUT` | `/admin/company/{id}` | Update company info |
| `DELETE` | `/admin/company/{id}` | Delete a company |
| `GET` | `/admin/students` | List all student profiles |
| `GET` | `/admin/applications` | List all applications |
| `GET` | `/admin/dashboard` | Placement statistics |

---

## Environment Variables

> ⚠️ **Never commit real credentials.** Add `application.properties` and `application-*.properties` to `.gitignore` (already done).

**`src/main/resources/application.properties`** (template):

```properties
# ── Application ────────────────────────────────────────────
spring.application.name=placement-backend
server.port=8080

# ── Database ───────────────────────────────────────────────
spring.datasource.url=jdbc:mysql://localhost:3306/nmamit_placement_portal?useSSL=false&serverTimezone=Asia/Kolkata
spring.datasource.username=YOUR_DB_USER
spring.datasource.password=YOUR_DB_PASSWORD
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# ── JPA / Hibernate ────────────────────────────────────────
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.format_sql=false

# ── Flyway ─────────────────────────────────────────────────
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration

# ── JWT ────────────────────────────────────────────────────
jwt.secret=REPLACE_WITH_256_BIT_BASE64_KEY
jwt.expiration=3600000

# ── File Upload ────────────────────────────────────────────
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
app.upload.dir=uploads/
```

---

## Roles

| Role | Description |
|---|---|
| `STUDENT` | Registered student — view drives, apply, manage own profile |
| `ADMIN` | Placement officer — full management + dashboard access |

---

## Development Roadmap

| Phase | Status | Focus |
|---|---|---|
| Phase 1 | ✅ Done | Project setup, MySQL, JWT auth, User entity |
| Phase 2 | 🔄 In progress | Student profile + education details |
| Phase 3 | 📋 Planned | Company management |
| Phase 4 | 📋 Planned | Job drives & applications |
| Phase 5 | 📋 Planned | Admin dashboard |
| Phase 6 | 📋 Planned | Swagger / OpenAPI documentation |
| Phase 7 | 📋 Planned | Flutter frontend |
| Phase 8 | 📋 Planned | Notifications system |

---

## Contributing

1. Fork the repository
2. Create a feature branch
   ```bash
   git checkout -b feature/your-feature-name
   ```
3. Commit using [Conventional Commits](https://www.conventionalcommits.org/)
   ```bash
   git commit -m "feat(auth): add refresh token endpoint"
   ```
4. Push and open a Pull Request

**Commit prefix guide:**
| Prefix | When to use |
|---|---|
| `feat` | New feature |
| `fix` | Bug fix |
| `docs` | Documentation only |
| `refactor` | Code change (no feature/fix) |
| `test` | Add or fix tests |
| `chore` | Build / tooling |

---

## License

This project is licensed under the **MIT License** — see [LICENSE](./LICENSE) for details.

---

> Built with ❤️ for **NMAM Institute of Technology**, Nitte — Karnataka, India.
