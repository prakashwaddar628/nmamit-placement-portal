import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../data/admin_repository.dart';
import '../../../student/domain/dashboard_models.dart';

final adminDashboardProvider = FutureProvider.autoDispose<AdminDashboard>((ref) {
  final repository = ref.watch(adminRepositoryProvider);
  return repository.getDashboard();
});
