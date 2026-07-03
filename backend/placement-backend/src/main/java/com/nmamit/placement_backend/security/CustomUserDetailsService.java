package com.nmamit.placement_backend.security;

import com.nmamit.placement_backend.entity.UserAccount;
import com.nmamit.placement_backend.repository.UserAccountRepository;

import lombok.RequiredArgsConstructor;

import org.springframework.security.core.userdetails.*;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    
    private final UserAccountRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        UserAccount user = repository.findByCollegeEmail(username)
        .orElseThrow(() -> 
        new UsernameNotFoundException("User not found"));
        
        return User.builder()
            .username(user.getCollegeEmail())
            .password(user.getPassword())
            .roles(user.getRole().name())
            .build();
    }
}
