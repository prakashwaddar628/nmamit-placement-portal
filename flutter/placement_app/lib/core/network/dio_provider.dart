import 'package:dio/dio.dart';
import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../constants/app_constants.dart';
import '../services/secure_storage_service.dart';

final dioProvider = Provider<Dio>((ref) {
  final storage = ref.read(secureStorageProvider);
  final dio = Dio(BaseOptions(
    baseUrl: AppConstants.baseUrl,
    connectTimeout: const Duration(milliseconds: AppConstants.connectTimeoutMs),
    receiveTimeout: const Duration(milliseconds: AppConstants.receiveTimeoutMs),
    headers: {
      'Content-Type': 'application/json',
      'Accept': 'application/json',
    },
  ));

  dio.interceptors.add(AuthInterceptor(dio, storage, ref));
  dio.interceptors.add(LogInterceptor(
    requestBody: false,
    responseBody: false,
    logPrint: (obj) => debugPrint('[DIO] $obj'),
  ));

  return dio;
});

class AuthInterceptor extends Interceptor {
  final Dio dio;
  final SecureStorageService storage;
  final Ref ref;

  AuthInterceptor(this.dio, this.storage, this.ref);

  @override
  void onRequest(RequestOptions options, RequestInterceptorHandler handler) async {
    final token = await storage.getToken();
    if (token != null) {
      options.headers['Authorization'] = 'Bearer $token';
    }
    handler.next(options);
  }

  @override
  void onError(DioException err, ErrorInterceptorHandler handler) async {
    if (err.response?.statusCode == 401) {
      // Clear token and redirect to login
      await storage.clearAll();
    }
    handler.next(err);
  }
}

void debugPrint(String message) {
  // ignore: avoid_print
  print(message);
}
