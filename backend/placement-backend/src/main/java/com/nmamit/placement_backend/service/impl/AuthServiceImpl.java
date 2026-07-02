package com.nmamit.placement_backend.service.impl;

import com.nmamit.placement_backend.dto.request.RegisterRequest;
import com.nmamit.placement_backend.dto.response.RegisterResponse;
import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.enums.Role;
import com.nmamit.placement_backend.repository.UserAccountRepository;
import com.nmamit.placement_backend.service.AuthService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final UserAccountRepository userAccountRepository;

    private final PasswordEncoder passwordEncoder;
    
    @Override
    public RegisterResponse register (RegisterRequest request) {
        if (userAccountRepository.existsByCollegeEmail(request.getCollegeEmail())) {
            throw new RuntimeException("User with this email already exists.");
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
}
