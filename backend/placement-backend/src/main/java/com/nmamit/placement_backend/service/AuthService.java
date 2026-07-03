package com.nmamit.placement_backend.service;

import com.nmamit.placement_backend.dto.request.RegisterRequest;
import com.nmamit.placement_backend.dto.response.RegisterResponse;
import com.nmamit.placement_backend.dto.request.LoginRequest;
import com.nmamit.placement_backend.dto.response.LoginResponse;

public interface AuthService {
    RegisterResponse register(RegisterRequest request);

    LoginResponse login(LoginRequest request);
}
