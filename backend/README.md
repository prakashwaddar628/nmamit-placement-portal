# placement-backend

Spring Boot 3 REST API for the NMAMIT Placement Portal — providing JWT authentication, student profile management, company drive tracking, and an admin dashboard.

---

## Table of Contents

- [Quick Start](#quick-start)
- [Tech Stack](#tech-stack)
- [Architecture](#architecture)
- [Database Migrations](#database-migrations)
- [Security](#security)
- [API Endpoints](#api-endpoints)
- [Configuration Reference](#configuration-reference)
- [Running Tests](#running-tests)
- [File Storage](#file-storage)

---

## Quick Start

```bash
# From repo root
cd backend/placement-backend

# Create DB
mysql -u root -p -e "CREATE DATABASE nmamit_placement_portal CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;"

# Run (Flyway migrations execute automatically on startup)
./mvnw spring-boot:run

# Swagger UI
open http://localhost:8080/swagger-ui/index.html
```

---

## Tech Stack

| Technology | Version | Purpose |
|---|---|---|
| Java | 21 LTS | Language & runtime |
| Spring Boot | 3.5.x | Application framework |
| Spring Security | 6.x | Authentication & authorisation |
| Spring Data JPA | Latest | ORM / repositories |
| Flyway | Latest | Database version control |
| MySQL Connector/J | 8.x | JDBC driver |
| Jakarta Validation | 3.x | Request bean validation |
| Lombok | Latest | Boilerplate reduction |
| springdoc-openapi | 2.x | Swagger / OpenAPI 3 docs |
| SLF4J + Logback | Latest | Structured logging |
| Spring DevTools | Runtime | Hot reload in development |
| JUnit 5 + MockMvc | Test | Unit & integration tests |

---

## Architecture

Clean layered architecture — no business logic in controllers, no DB calls in services.

```
com.nmamit.placement_backend/
│
├── common/                  # ApiResponse<T> — unified response envelope
│
├── config/                  # ApplicationConfig
│   └── ApplicationConfig    # UserDetailsService, AuthenticationProvider,
│                            # AuthenticationManager, PasswordEncoder beans
│
├── controller/              # HTTP layer — thin, delegates to services
│   └── AuthController       # POST /api/auth/register  /login
│
├── dto/
│   ├── request/             # Inbound payloads (validated with @Valid)
│   │   ├── LoginRequest
│   │   └── RegisterRequest
│   └── response/            # Outbound shapes (never expose entities)
│       ├── LoginResponse
│       └── RegisterResponse
│
├── entity/                  # JPA-mapped domain objects
│   └── UserAccount          # @Entity → table: users
│
├── enums/
│   └── Role                 # ADMIN | STUDENT
│
├── exception/               # @ControllerAdvice global error handling
│
├── repository/              # Spring Data JPA interfaces
│
├── security/                # Spring Security configuration
│   ├── SecurityConfig       # Filter chain — stateless JWT, CORS, CSRF
│   ├── JwtService           # Token generation, validation, claims
│   ├── JwtAuthenticationFilter   # Per-request JWT extraction & auth
│   └── CustomUserDetailsService  # Load user by college email
│
├── service/                 # Business logic interfaces
│   ├── AuthService
│   └── impl/
│       └── AuthServiceImpl
│
├── util/                    # Utility helpers (file handling etc.)
│
└── validation/              # Custom constraint validators
```

---

## Database Migrations

Managed by **Flyway**. All scripts in `src/main/resources/db/migration/` run in version order on startup.

| Version | File | Table Created | Notes |
|---|---|---|---|
| V1 | `V1__create_users.sql` | `users` | Auth credentials, role, active flag |
| V2 | `V2__create_student_profile.sql` | `student_profile` | USN, personal/academic/social details |
| V3 | `V3__create_education_details.sql` | `education_details` | SSLC → PG academic history (per-level) |

### `education_details` Schema

```sql
CREATE TABLE education_details (
    id                  BIGINT          NOT NULL AUTO_INCREMENT,
    student_id          BIGINT          NOT NULL,             -- FK → student_profile.id
    education_type      ENUM(...)       NOT NULL,             -- SSLC|PUC|DIPLOMA|UG|PG
    institution_name    VARCHAR(200)    NOT NULL,
    board_or_university VARCHAR(150)    DEFAULT NULL,
    specialization      VARCHAR(150)    DEFAULT NULL,
    percentage          DECIMAL(5, 2)  DEFAULT NULL,
    cgpa                DECIMAL(4, 2)  DEFAULT NULL,
    passing_year        YEAR           DEFAULT NULL,
    created_at          TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP      NOT NULL DEFAULT CURRENT_TIMESTAMP
                            ON UPDATE CURRENT_TIMESTAMP,
    PRIMARY KEY (id),
    CONSTRAINT fk_education_student
        FOREIGN KEY (student_id) REFERENCES student_profile (id)
        ON DELETE CASCADE
) ENGINE = InnoDB DEFAULT CHARSET = utf8mb4 COLLATE = utf8mb4_unicode_ci;
```

> **Note**: Both `percentage` and `cgpa` are stored — institutions use different grading systems. The application layer ensures at least one is provided.

---

## Security

| Mechanism | Detail |
|---|---|
| Authentication | JWT Bearer tokens (stateless) |
| Token signing | HMAC-SHA256 (HS256) |
| Password hashing | BCrypt |
| Session policy | `STATELESS` — no HTTP sessions |
| CSRF | Disabled (API-only, no browser form submissions) |
| Authorisation | Method/URL-level role checks via `SecurityFilterChain` |

### Public endpoints (no token required)

```
POST  /api/auth/register
POST  /api/auth/login
GET   /swagger-ui/**
GET   /v3/api-docs/**
```

All other endpoints require a valid `Authorization: Bearer <token>` header.

### JWT Flow

```
Client                            Server
  │                                  │
  │── POST /api/auth/login ─────────>│
  │                                  │ Validate credentials
  │<── { accessToken, expiresIn } ───│
  │                                  │
  │── GET /students/profile ────────>│
  │   Authorization: Bearer <token>  │ JwtAuthenticationFilter validates token
  │<── 200 { profile data } ─────────│
```

---

## API Endpoints

> All responses are wrapped in `ApiResponse<T> { success, message, data }`.

### Auth

```
POST  /api/auth/register   → RegisterResponse
POST  /api/auth/login      → LoginResponse { accessToken, tokenType, expiresIn }
```

### Student *(ROLE_STUDENT)*

```
GET   /students/profile
PUT   /students/profile
POST  /students/photo      (multipart/form-data)
POST  /students/resume     (multipart/form-data, PDF only)
```

### Companies *(any authenticated user)*

```
GET   /companies
GET   /companies/{id}
GET   /companies/upcoming
GET   /companies/previous
```

### Applications *(ROLE_STUDENT)*

```
POST  /applications/apply
GET   /applications/my
GET   /applications/status/{id}
```

### Admin *(ROLE_ADMIN)*

```
POST    /admin/company
PUT     /admin/company/{id}
DELETE  /admin/company/{id}
GET     /admin/students
GET     /admin/applications
GET     /admin/dashboard
```

---

## Configuration Reference

`src/main/resources/application.properties`:

```properties
spring.application.name=placement-backend
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/nmamit_placement_portal
spring.datasource.username=root
spring.datasource.password=root
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver

# JPA
spring.jpa.hibernate.ddl-auto=validate   # Use 'validate' with Flyway
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true

# Flyway
spring.flyway.enabled=true
spring.flyway.baseline-on-migrate=true
spring.flyway.locations=classpath:db/migration

# JWT
jwt.secret=<256-bit Base64 key>
jwt.expiration=3600000                   # 1 hour in milliseconds

# File upload limits
spring.servlet.multipart.max-file-size=10MB
spring.servlet.multipart.max-request-size=10MB
app.upload.dir=uploads/
```

> ⚠️ `application.properties` with real credentials is excluded from git via `.gitignore`.

---

## Running Tests

```bash
./mvnw test

# With coverage report
./mvnw verify

# Windows
mvnw.cmd test
```

---

## File Storage

Files are stored locally under the `uploads/` directory (git-ignored):

```
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
```

Served via `/uploads/**` static resource mapping or a dedicated file controller. Cloud storage (e.g., AWS S3) can be added in a later phase.

---

> Part of the [NMAMIT Placement Portal](../../README.md) monorepo.