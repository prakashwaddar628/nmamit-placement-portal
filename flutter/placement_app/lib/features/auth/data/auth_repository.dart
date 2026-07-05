import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../../../core/constants/app_constants.dart';
import '../../../core/network/dio_provider.dart';
import '../../../core/services/secure_storage_service.dart';
import '../../../shared/models/api_response.dart';
import '../domain/auth_models.dart';

final authRepositoryProvider = Provider<AuthRepository>((ref) {
  return AuthRepository(
    ref.read(dioProvider),
    ref.read(secureStorageProvider),
  );
});

class AuthRepository {
  final Dio _dio;
  final SecureStorageService _storage;

  AuthRepository(this._dio, this._storage);

  Future<LoginResponse> login(String email, String password) async {
    final response = await _dio.post(
      AppConstants.authLogin,
      data: {'collegeEmail': email, 'password': password},
    );
    final apiResponse = ApiResponse.fromJson(
      response.data as Map<String, dynamic>,
      (data) => LoginResponse.fromJson(data as Map<String, dynamic>),
    );
    if (!apiResponse.success || apiResponse.data == null) {
      throw Exception(apiResponse.message);
    }
    return apiResponse.data!;
  }

  Future<RegisterResponse> register(String email, String password, String role) async {
    final response = await _dio.post(
      AppConstants.authRegister,
      data: {'collegeEmail': email, 'password': password, 'role': role},
    );
    final apiResponse = ApiResponse.fromJson(
      response.data as Map<String, dynamic>,
      (data) => RegisterResponse.fromJson(data as Map<String, dynamic>),
    );
    if (!apiResponse.success || apiResponse.data == null) {
      throw Exception(apiResponse.message);
    }
    return apiResponse.data!;
  }

  Future<void> saveSession(LoginResponse loginResponse, String email) async {
    await _storage.saveToken(loginResponse.token);
    await _storage.saveRole(loginResponse.role);
    await _storage.saveEmail(email);
  }

  Future<bool> isLoggedIn() async {
    return _storage.hasToken();
  }

  Future<String?> getRole() async {
    return _storage.getRole();
  }

  Future<String?> getEmail() async {
    return _storage.getEmail();
  }

  Future<void> logout() async {
    await _storage.clearAll();
  }
}
