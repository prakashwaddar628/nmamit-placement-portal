package com.nmamit.placement_backend.service.impl;

import com.nmamit.placement_backend.common.exception.EmailAlreadyExistsException;
import com.nmamit.placement_backend.dto.request.LoginRequest;
import com.nmamit.placement_backend.dto.request.RegisterRequest;
import com.nmamit.placement_backend.dto.response.LoginResponse;
import com.nmamit.placement_backend.dto.response.RegisterResponse;
import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.enums.Role;
import com.nmamit.placement_backend.repository.UserAccountRepository;
import com.nmamit.placement_backend.service.AuthService;
import com.nmamit.placement_backend.security.JwtService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserAccountRepository userAccountRepository;

    private final PasswordEncoder passwordEncoder;

    private final JwtService jwtService;
    
    @Override
    public RegisterResponse register (RegisterRequest request) {
        if (userAccountRepository.existsByCollegeEmail(request.getCollegeEmail())) {
            throw new EmailAlreadyExistsException(request.getCollegeEmail() +" Email already exists");
        }

        String encodedPassword = passwordEncoder.encode(request.getPassword());

        UserAccount userAccount = UserAccount.builder()
        .collegeEmail(request.getCollegeEmail())
        .password(encodedPassword)
        .role(Role.STUDENT)
        .active(true)
        .build();

        userAccountRepository.save(userAccount);

        return RegisterResponse.builder()
        .message("Registration successful")
        .collegeEmail(userAccount.getCollegeEmail())
        .build();
    }

    @Override
    public LoginResponse login(LoginRequest request) {

        UserAccount user = userAccountRepository
                .findByCollegeEmail(request.getCollegeEmail())
                .orElseThrow(() ->
                        new RuntimeException("Invalid email or password"));

        if (!passwordEncoder.matches(
                request.getPassword(),
                user.getPassword())) {

            throw new RuntimeException("Invalid email or password");
        }

        String token = jwtService.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .tokenType("Bearer")
                .collegeEmail(user.getCollegeEmail())
                .role(user.getRole().name())
                .build();
    }
}
