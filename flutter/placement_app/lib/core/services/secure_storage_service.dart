import 'package:flutter_riverpod/flutter_riverpod.dart';
import 'package:flutter_secure_storage/flutter_secure_storage.dart';
import '../constants/app_constants.dart';

final secureStorageProvider = Provider<SecureStorageService>((ref) {
  return SecureStorageService();
});

class SecureStorageService {
  final _storage = const FlutterSecureStorage(
    aOptions: AndroidOptions(encryptedSharedPreferences: true),
    iOptions: IOSOptions(accessibility: KeychainAccessibility.first_unlock),
  );

  Future<void> saveToken(String token) async {
    await _storage.write(key: AppConstants.jwtKey, value: token);
  }

  Future<String?> getToken() async {
    return _storage.read(key: AppConstants.jwtKey);
  }

  Future<void> saveRole(String role) async {
    await _storage.write(key: AppConstants.userRoleKey, value: role);
  }

  Future<String?> getRole() async {
    return _storage.read(key: AppConstants.userRoleKey);
  }

  Future<void> saveEmail(String email) async {
    await _storage.write(key: AppConstants.userEmailKey, value: email);
  }

  Future<String?> getEmail() async {
    return _storage.read(key: AppConstants.userEmailKey);
  }

  Future<bool> hasToken() async {
    final token = await getToken();
    return token != null && token.isNotEmpty;
  }

  Future<void> clearAll() async {
    await _storage.deleteAll();
  }
}
