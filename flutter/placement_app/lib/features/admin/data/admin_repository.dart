import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/constants/app_constants.dart';
import '../../../core/network/dio_provider.dart';
import '../../../shared/models/api_response.dart';
import '../../student/domain/dashboard_models.dart';

final adminRepositoryProvider = Provider<AdminRepository>((ref) {
  return AdminRepository(ref.read(dioProvider));
});

class AdminRepository {
  final Dio _dio;

  AdminRepository(this._dio);

  Future<AdminDashboard> getDashboard() async {
    final response = await _dio.get(AppConstants.adminDashboard);
    final apiResponse = ApiResponse.fromJson(
      response.data as Map<String, dynamic>,
      (data) => AdminDashboard.fromJson(data as Map<String, dynamic>),
    );
    if (!apiResponse.success || apiResponse.data == null) {
      throw Exception(apiResponse.message);
    }
    return apiResponse.data!;
  }
}
