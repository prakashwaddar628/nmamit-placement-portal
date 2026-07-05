import 'package:flutter/material.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:go_router/go_router.dart';
import '../../features/auth/presentation/auth_provider.dart';
import '../../features/auth/presentation/login_screen.dart';
import '../../features/auth/presentation/splash_screen.dart';
import '../../features/student/presentation/student_dashboard_screen.dart';
import '../../features/student/presentation/student_profile_screen.dart';
import '../../features/student/presentation/education_screen.dart';
import '../../features/student/presentation/job_drives_screen.dart';
import '../../features/student/presentation/my_applications_screen.dart';
import '../../features/student/presentation/resume_screen.dart';
import '../../features/admin/presentation/admin_dashboard_screen.dart';
import '../../features/admin/presentation/admin_stubs.dart';
import '../../shared/widgets/student_shell.dart';
import '../../shared/widgets/admin_shell.dart';
import '../constants/app_constants.dart';

final appRouterProvider = Provider<GoRouter>((ref) {
  final authState = ref.watch(authNotifierProvider);

  return GoRouter(
    initialLocation: '/splash',
    redirect: (context, state) {
      final isLoading = authState is AuthLoading || authState is AuthInitial;
      final isAuth = authState is AuthAuthenticated;
      final isUnauthenticated = authState is AuthUnauthenticated || authState is AuthError;

      final onSplash = state.matchedLocation == '/splash';
      final onLogin = state.matchedLocation == '/login';

      if (isLoading) return onSplash ? null : '/splash';
      if (isUnauthenticated && !onLogin) return '/login';
      if (isAuth) {
        if (onSplash || onLogin) {
          final role = (authState as AuthAuthenticated).role;
          return role == AppConstants.roleAdmin ? '/admin/dashboard' : '/student/dashboard';
        }
      }
      return null;
    },
    routes: [
      GoRoute(path: '/splash', builder: (_, __) => const SplashScreen()),
      GoRoute(path: '/login', builder: (_, __) => const LoginScreen()),

      // Student Shell
      ShellRoute(
        builder: (context, state, child) => StudentShell(child: child),
        routes: [
          GoRoute(path: '/student/dashboard', builder: (_, __) => const StudentDashboardScreen()),
          GoRoute(path: '/student/profile', builder: (_, __) => const StudentProfileScreen()),
          GoRoute(path: '/student/education', builder: (_, __) => const EducationScreen()),
          GoRoute(path: '/student/job-drives', builder: (_, __) => const JobDrivesScreen()),
          GoRoute(path: '/student/applications', builder: (_, __) => const MyApplicationsScreen()),
          GoRoute(path: '/student/resume', builder: (_, __) => const ResumeScreen()),
        ],
      ),

      // Admin Shell
      ShellRoute(
        builder: (context, state, child) => AdminShell(child: child),
        routes: [
          GoRoute(path: '/admin/dashboard', builder: (_, __) => const AdminDashboardScreen()),
          GoRoute(path: '/admin/students', builder: (_, __) => const AdminStudentsScreen()),
          GoRoute(path: '/admin/companies', builder: (_, __) => const AdminCompaniesScreen()),
          GoRoute(path: '/admin/job-drives', builder: (_, __) => const AdminJobDrivesScreen()),
          GoRoute(path: '/admin/applications', builder: (_, __) => const AdminApplicationsScreen()),
        ],
      ),
    ],
    errorBuilder: (context, state) => Scaffold(
      body: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          children: [
            const Icon(Icons.error_outline, size: 64, color: Colors.red),
            const SizedBox(height: 16),
            Text('Page not found: ${state.matchedLocation}'),
            TextButton(
              onPressed: () => context.go('/splash'),
              child: const Text('Go Home'),
            ),
          ],
        ),
      ),
    ),
  );
});
