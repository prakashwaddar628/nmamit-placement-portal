# NMAMIT Placement Portal

A full-stack placement management system built for **NMAM Institute of Technology (NMAMIT)**, designed to streamline the campus recruitment process for students and administrators.

---

## 📋 Table of Contents

- [Overview](#overview)
- [Features](#features)
- [Tech Stack](#tech-stack)
- [Project Structure](#project-structure)
- [Getting Started](#getting-started)
  - [Prerequisites](#prerequisites)
  - [Backend Setup](#backend-setup)
  - [Flutter Setup](#flutter-setup)
- [API Reference](#api-reference)
- [Environment Variables](#environment-variables)
- [Contributing](#contributing)
- [License](#license)

---

## Overview

The NMAMIT Placement Portal is a monorepo containing:

- **`backend/`** — Spring Boot REST API (Java 21) with JWT-based authentication, MySQL database, and role-based access control.
- **`flutter/`** — Cross-platform mobile/web frontend built with Flutter.
- **`documentation/`** — Project design documents, API specs, and database schemas.

---

## Features

### Student
- 🔐 Register & login with JWT authentication
- 👤 Manage personal profile, photo, and resume
- 🏢 Browse upcoming and past company drives
- 📝 Apply for placement drives and track application status
- 🔔 Receive notifications and announcements

### Admin
- 🏗️ Manage companies and job drives (CRUD)
- 👥 View all student profiles and applications
- 📊 Access placement dashboard with statistics
- 📢 Send announcements and notifications
- 👔 Manage placement team profiles

---

## Tech Stack

### Backend
| Technology       | Version / Details      |
|------------------|------------------------|
| Java             | 21 (LTS)               |
| Spring Boot      | 3.5.x                  |
| Maven            | Latest                 |
| MySQL            | 8.x                    |
| Spring Security  | JWT (Bearer Token)     |
| Spring Data JPA  | Latest                 |
| Jakarta Validation | Bean Validation      |
| Lombok           | Latest                 |
| Swagger / OpenAPI | springdoc-openapi 2.x |
| Logging          | SLF4J + Logback        |

### Frontend
| Technology | Details          |
|------------|------------------|
| Flutter    | Cross-platform   |
| Dart       | Latest stable    |

---

## Project Structure

```
nmamit-placement-portal/
├── backend/
│   └── placement-backend/          # Spring Boot application
│       ├── src/
│       │   └── main/java/com/nmamit/placement_backend/
│       │       ├── config/         # Security & app configuration
│       │       ├── controller/     # REST API controllers
│       │       ├── dto/            # Data Transfer Objects
│       │       ├── entity/         # JPA entities
│       │       ├── enums/          # Role & status enums
│       │       ├── exception/      # Global exception handling
│       │       ├── repository/     # Spring Data JPA repositories
│       │       ├── security/       # JWT filter & auth components
│       │       ├── service/        # Business logic
│       │       ├── util/           # Utility classes
│       │       └── validation/     # Custom validators
│       ├── uploads/                # Local file storage
│       │   ├── resumes/
│       │   ├── photos/
│       │   └── job-descriptions/
│       └── pom.xml
├── flutter/                        # Flutter app
├── documentation/                  # Design docs & schemas
├── .gitignore
├── LICENSE
└── README.md
```

---

## Getting Started

### Prerequisites

- **Java 21** (LTS) — [Download](https://adoptium.net/)
- **Maven 3.9+** — [Download](https://maven.apache.org/download.cgi)
- **MySQL 8.x** — [Download](https://dev.mysql.com/downloads/)
- **Flutter SDK** — [Install](https://docs.flutter.dev/get-started/install)
- **Git**

---

### Backend Setup

1. **Clone the repository**
   ```bash
   git clone https://github.com/prakashwaddar628/nmamit-placement-portal.git
   cd nmamit-placement-portal/backend/placement-backend
   ```

2. **Create the MySQL database**
   ```sql
   CREATE DATABASE placement_portal;
   ```

3. **Configure environment variables** (see [Environment Variables](#environment-variables))

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```
   Or on Windows:
   ```cmd
   mvnw.cmd spring-boot:run
   ```

5. **Access Swagger UI**
   ```
   http://localhost:8080/swagger-ui/index.html
   ```

---

### Flutter Setup

> 🚧 Flutter app is under development.

```bash
cd flutter/
flutter pub get
flutter run
```

---

## API Reference

Full API documentation is available via Swagger at `/swagger-ui/index.html` when the backend is running.

### Authentication
| Method | Endpoint                | Description           |
|--------|-------------------------|-----------------------|
| POST   | `/api/auth/register`    | Register a new student |
| POST   | `/api/auth/login`       | Login and get JWT     |
| POST   | `/api/auth/refresh`     | Refresh access token  |
| GET    | `/api/auth/me`          | Get current user info |

### Student
| Method | Endpoint                | Description              |
|--------|-------------------------|--------------------------|
| GET    | `/students/profile`     | Get student profile      |
| PUT    | `/students/profile`     | Update student profile   |
| POST   | `/students/photo`       | Upload profile photo     |
| POST   | `/students/resume`      | Upload resume (PDF)      |

### Companies & Drives
| Method | Endpoint                | Description              |
|--------|-------------------------|--------------------------|
| GET    | `/companies`            | List all companies       |
| GET    | `/companies/{id}`       | Get company details      |
| GET    | `/companies/upcoming`   | Get upcoming drives      |
| GET    | `/companies/previous`   | Get past drives          |

### Applications
| Method | Endpoint                    | Description              |
|--------|-----------------------------|--------------------------|
| POST   | `/applications/apply`       | Apply for a drive        |
| GET    | `/applications/my`          | My applications          |
| GET    | `/applications/status/{id}` | Application status       |

### Admin
| Method | Endpoint                  | Description              |
|--------|---------------------------|--------------------------|
| POST   | `/admin/company`          | Add a company            |
| PUT    | `/admin/company/{id}`     | Update company info      |
| DELETE | `/admin/company/{id}`     | Remove a company         |
| GET    | `/admin/students`         | View all students        |
| GET    | `/admin/applications`     | View all applications    |
| GET    | `/admin/dashboard`        | Dashboard statistics     |

---

## Environment Variables

Create an `application.properties` or `application.yml` in `backend/placement-backend/src/main/resources/` and configure:

```properties
# Database
spring.datasource.url=jdbc:mysql://localhost:3306/placement_portal
spring.datasource.username=YOUR_DB_USERNAME
spring.datasource.password=YOUR_DB_PASSWORD

# JPA
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=false

# JWT
app.jwt.secret=YOUR_JWT_SECRET_KEY_MIN_256_BITS
app.jwt.expiration-ms=86400000

# File Upload
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
app.upload.dir=uploads/
```

> ⚠️ **Never commit `application.properties` with real credentials to version control.**

---

## Roles

| Role          | Description                                      |
|---------------|--------------------------------------------------|
| `ROLE_STUDENT` | Registered students — can view drives, apply, manage profile |
| `ROLE_ADMIN`   | Placement officer — full management access       |

---

## Contributing

1. Fork the repository
2. Create a feature branch: `git checkout -b feature/your-feature-name`
3. Commit your changes: `git commit -m "feat: add your feature"`
4. Push to your branch: `git push origin feature/your-feature-name`
5. Open a Pull Request

Please follow [Conventional Commits](https://www.conventionalcommits.org/) for commit messages.

---

## License

This project is licensed under the terms of the [LICENSE](./LICENSE) file in the root of this repository.

---

> Built with ❤️ for NMAM Institute of Technology, Nitte.
