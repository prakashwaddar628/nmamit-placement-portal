package com.nmamit.placement_backend.controller;

import com.nmamit.placement_backend.dto.request.RegisterRequest;
import com.nmamit.placement_backend.dto.response.RegisterResponse;
import com.nmamit.placement_backend.service.AuthService;
import com.nmamit.placement_backend.dto.request.LoginRequest;
import com.nmamit.placement_backend.dto.response.LoginResponse;

import jakarta.validation.Valid;

import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController // this tells spring that this class is a controller and it will handle the incoming requests
@RequestMapping("/api/auth")
@RequiredArgsConstructor // this tells spring to create a constructor for this class and inject the dependencies
public class AuthController {
    
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<RegisterResponse> register(
        @Valid @RequestBody RegisterRequest request) {
        RegisterResponse response = authService.register(request);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(
        @Valid @RequestBody LoginRequest request) {

            return ResponseEntity.ok(authService.login(request));

        }
    
}
