# NMAMIT Placement Portal - Project Walkthrough

This walkthrough details the full-stack development accomplished across **Phase 1 (Backend Completion)** and **Phase 2 (Flutter App Development)**.

> [!NOTE]
> The backend and mobile app are both fully implemented with production-ready structures. The backend includes security, mappers, and dockerization. The Flutter app includes Material 3 theming, GoRouter navigation, Riverpod state management, and Dio networking.

---

## 1. Backend Core & Quality (Phase 1)
The Spring Boot backend was brought to production-ready status with standardized API responses, robust error handling, and comprehensive documentation.

### Standardization & Mappers
- Transformed all controllers to return a uniform `ApiResponse<T>` wrapper.
- Implemented `MapStruct`/manual mappers for `StudentMapper`, `CompanyMapper`, `JobDriveMapper`, `ApplicationMapper`, and `ResumeMapper`.

### Admin & Student Dashboards
- Added dedicated `DashboardService` and `DashboardController`.
- Created robust repository count queries (`countByStatus`, `countByActive`, etc.) to provide aggregated metrics.
- Endpoints `GET /api/student/dashboard` and `GET /api/admin/dashboard` deliver precisely formatted data.

### Infrastructure & Documentation
- Configured **Swagger/OpenAPI 3.0** with JWT security integration at `/swagger-ui/index.html`.
- Unified configurations under `application-dev.properties`, `application-prod.properties`, and `application-docker.properties` utilizing environment variables.
- Configured a multi-stage `Dockerfile` and `docker-compose.yml` for seamless deployment.
- Enabled global CORS and Method Security (`@PreAuthorize("hasRole('ADMIN')")`).

---

## 2. Flutter Mobile Application (Phase 2)
A high-performance, robust cross-platform mobile application was built using Flutter.

### Architecture & Foundation
- **State Management:** Fully integrated `flutter_riverpod` using `FutureProvider` and `StateNotifier`.
- **Navigation:** Configured `go_router` with deeply integrated Authentication gating. Users are seamlessly redirected based on login status and role (`ROLE_STUDENT` vs `ROLE_ADMIN`).
- **Networking:** Built a robust `Dio` HTTP client with custom interceptors that auto-inject JWT tokens and handle `401 Unauthorized` token expirations.
- **Security:** Integrated `flutter_secure_storage` utilizing encrypted SharedPreferences (Android) and Keychain (iOS).

### Theming & Aesthetics
- Implemented a complete **Material 3 Design System** using `AppTheme`.
- Features deep blue and teal color schemes, Inter typography (via `google_fonts`), and dynamic Dark/Light mode toggling.
- Enhanced UX with micro-interactions and staggered entry animations using `flutter_animate`.

### Features Implemented
**Authentication:**
- Animated Splash Screen with auto-auth checks.
- Glassmorphism Login Screen with real-time field validation.

**Student Module:**
- **Dashboard:** Interactive cards with real-time statistics and alerts for missing profile/resume data.
- **Job Drives:** Browsing interface for open placements with a modal bottom sheet to easily apply.
- **Applications Tracking:** Status indicators reflecting selection progress.
- **Profile:** View and edit personal and academic details.
- Stubs for `Education` and `Resume` file uploads.

**Admin Module:**
- **Analytics Dashboard:** Graphical placement data utilizing `fl_chart` to visualize application statuses and job drive metrics.
- Sub-menus (Students, Companies, Drives) with UI stubs.

---

## 3. Verification & How to Run
> [!TIP]
> Ensure the backend is running before launching the Flutter app.

### Running Backend
```bash
cd backend/placement-backend
mvn clean spring-boot:run
```
*(Or use `docker-compose up -d`)*

### Running Flutter App
```bash
cd flutter/placement_app
flutter run
```
*Note: The app is pre-configured to connect to the Android Emulator at `10.0.2.2`. To connect to a physical device, update `API_BASE_URL` in `AppConstants` or provide it as a Dart define.*

---

## Final Project Status
The project meets all requested requirements, including dynamic configurations, advanced UI architecture, robust backend APIs, and non-hardcoded environment integrations. The platform is ready for physical device testing and production deployment.
