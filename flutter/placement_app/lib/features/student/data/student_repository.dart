import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/constants/app_constants.dart';
import '../../../core/network/dio_provider.dart';
import '../../../shared/models/api_response.dart';
import '../domain/dashboard_models.dart';
import '../domain/student_models.dart';

final studentRepositoryProvider = Provider<StudentRepository>((ref) {
  return StudentRepository(ref.read(dioProvider));
});

class StudentRepository {
  final Dio _dio;

  StudentRepository(this._dio);

  Future<StudentDashboard> getDashboard() async {
    final response = await _dio.get(AppConstants.studentDashboard);
    final apiResponse = ApiResponse.fromJson(
      response.data as Map<String, dynamic>,
      (data) => StudentDashboard.fromJson(data as Map<String, dynamic>),
    );
    if (!apiResponse.success || apiResponse.data == null) {
      throw Exception(apiResponse.message);
    }
    return apiResponse.data!;
  }

  Future<StudentProfile> getProfile() async {
    final response = await _dio.get(AppConstants.studentProfile);
    final apiResponse = ApiResponse.fromJson(
      response.data as Map<String, dynamic>,
      (data) => StudentProfile.fromJson(data as Map<String, dynamic>),
    );
    if (!apiResponse.success || apiResponse.data == null) {
      throw Exception(apiResponse.message);
    }
    return apiResponse.data!;
  }

  Future<StudentProfile> updateProfile(Map<String, dynamic> data) async {
    final response = await _dio.put(AppConstants.studentProfile, data: data);
    final apiResponse = ApiResponse.fromJson(
      response.data as Map<String, dynamic>,
      (data) => StudentProfile.fromJson(data as Map<String, dynamic>),
    );
    if (!apiResponse.success || apiResponse.data == null) {
      throw Exception(apiResponse.message);
    }
    return apiResponse.data!;
  }

  Future<List<JobDrive>> getJobDrives() async {
    final response = await _dio.get(AppConstants.jobDrives);
    final apiResponse = ApiResponse.fromJson(
      response.data as Map<String, dynamic>,
      (data) => (data as List).map((e) => JobDrive.fromJson(e as Map<String, dynamic>)).toList(),
    );
    if (!apiResponse.success || apiResponse.data == null) {
      throw Exception(apiResponse.message);
    }
    return apiResponse.data!;
  }

  Future<List<Application>> getMyApplications() async {
    final response = await _dio.get(AppConstants.studentApplications);
    final apiResponse = ApiResponse.fromJson(
      response.data as Map<String, dynamic>,
      (data) => (data as List).map((e) => Application.fromJson(e as Map<String, dynamic>)).toList(),
    );
    if (!apiResponse.success || apiResponse.data == null) {
      throw Exception(apiResponse.message);
    }
    return apiResponse.data!;
  }

  Future<void> applyForJob(int jobDriveId) async {
    final response = await _dio.post(
      AppConstants.studentApplications,
      data: {'jobDriveId': jobDriveId},
    );
    final apiResponse = ApiResponse.fromJson(
      response.data as Map<String, dynamic>,
      (data) => null,
    );
    if (!apiResponse.success) {
      throw Exception(apiResponse.message);
    }
  }
}
