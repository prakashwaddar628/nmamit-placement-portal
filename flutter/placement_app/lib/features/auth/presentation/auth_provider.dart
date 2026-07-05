import 'package:flutter_riverpod/flutter_riverpod.dart';
import '../data/auth_repository.dart';

// Auth state
sealed class AuthState {}
class AuthInitial extends AuthState {}
class AuthLoading extends AuthState {}
class AuthAuthenticated extends AuthState {
  final String role;
  final String email;
  AuthAuthenticated({required this.role, required this.email});
}
class AuthUnauthenticated extends AuthState {}
class AuthError extends AuthState {
  final String message;
  AuthError(this.message);
}

final authNotifierProvider = StateNotifierProvider<AuthNotifier, AuthState>((ref) {
  return AuthNotifier(ref.read(authRepositoryProvider));
});

class AuthNotifier extends StateNotifier<AuthState> {
  final AuthRepository _repository;

  AuthNotifier(this._repository) : super(AuthInitial());

  Future<void> checkAuthStatus() async {
    final isLoggedIn = await _repository.isLoggedIn();
    if (isLoggedIn) {
      final role = await _repository.getRole();
      final email = await _repository.getEmail();
      if (role != null && email != null) {
        state = AuthAuthenticated(role: role, email: email);
        return;
      }
    }
    state = AuthUnauthenticated();
  }

  Future<void> login(String email, String password) async {
    state = AuthLoading();
    try {
      final loginResponse = await _repository.login(email, password);
      await _repository.saveSession(loginResponse, email);
      state = AuthAuthenticated(
        role: loginResponse.role,
        email: email,
      );
    } catch (e) {
      state = AuthError(_extractMessage(e));
    }
  }

  Future<void> logout() async {
    await _repository.logout();
    state = AuthUnauthenticated();
  }

  String _extractMessage(Object e) {
    if (e is Exception) {
      return e.toString().replaceAll('Exception: ', '');
    }
    return 'An unexpected error occurred';
  }
}
