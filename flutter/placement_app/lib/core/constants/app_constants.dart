class AppConstants {
  AppConstants._();

  // Base URL — override via environment variable or configure for different environments
  static const String baseUrl = String.fromEnvironment(
    'API_BASE_URL',
    defaultValue: 'http://10.0.2.2:8080/api', // 10.0.2.2 = host machine from Android emulator
  );

  // Auth
  static const String jwtKey = 'jwt_token';
  static const String userRoleKey = 'user_role';
  static const String userEmailKey = 'user_email';

  // API Endpoints
  static const String authRegister = '/auth/register';
  static const String authLogin = '/auth/login';

  static const String studentProfile = '/student/profile';
  static const String studentEducation = '/student/education';
  static const String studentApplications = '/student/applications';
  static const String studentResume = '/student/resume';
  static const String studentDashboard = '/student/dashboard';

  static const String jobDrives = '/job-drives';
  static const String companies = '/companies';

  static const String adminDashboard = '/admin/dashboard';
  static const String adminStudents = '/admin/students';
  static const String adminCompanies = '/admin/companies';
  static const String adminJobDrives = '/admin/job-drives';
  static const String adminApplications = '/admin/applications';

  // App settings
  static const int connectTimeoutMs = 15000;
  static const int receiveTimeoutMs = 15000;

  // Roles
  static const String roleStudent = 'ROLE_STUDENT';
  static const String roleAdmin = 'ROLE_ADMIN';
}
