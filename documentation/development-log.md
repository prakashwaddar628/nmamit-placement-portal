# NMAMIT Placement Portal - Development Log

---

## Sprint 1 – Student Registration Module

### Objective
Implement a secure student registration module using Spring Boot.

### Features Completed
- Spring Boot project initialization
- MySQL integration
- UserAccount entity
- UserAccountRepository
- Role Enum
- Registration API
- Password hashing using BCrypt
- Request validation
- Custom Exception Handling
- Global Exception Handler
- Standard API Response
- Spring Security Configuration

### API

POST /api/auth/register

### Request

```json
{
  "collegeEmail": "4nm21cs001@nmamit.in",
  "password": "Password@123"
}
```

### Response

```json
{
  "message": "Registration successful",
  "collegeEmail": "4nm21cs001@nmamit.in"
}
```

### Technologies Used

- Java 21
- Spring Boot
- Spring Data JPA
- Spring Security
- Hibernate
- MySQL
- Maven
- Lombok

### Concepts Learned

- Layered Architecture
- DTO Pattern
- Dependency Injection
- Spring Beans
- BCrypt Password Encoding
- Validation
- Repository Pattern
- Global Exception Handling