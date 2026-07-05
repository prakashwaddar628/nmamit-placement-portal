import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../data/student_repository.dart';
import '../domain/dashboard_models.dart';
import '../domain/student_models.dart';

final studentDashboardProvider = FutureProvider.autoDispose<StudentDashboard>((ref) {
  final repository = ref.watch(studentRepositoryProvider);
  return repository.getDashboard();
});

final studentProfileProvider = FutureProvider.autoDispose<StudentProfile>((ref) {
  final repository = ref.watch(studentRepositoryProvider);
  return repository.getProfile();
});

final jobDrivesProvider = FutureProvider.autoDispose<List<JobDrive>>((ref) {
  final repository = ref.watch(studentRepositoryProvider);
  return repository.getJobDrives();
});

final myApplicationsProvider = FutureProvider.autoDispose<List<Application>>((ref) {
  final repository = ref.watch(studentRepositoryProvider);
  return repository.getMyApplications();
});
