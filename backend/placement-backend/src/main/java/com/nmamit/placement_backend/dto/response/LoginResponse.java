package com.nmamit.placement_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class LoginResponse {
    
    private String token;

    private String tokenType;

    private String collegeEmail;

    private String role;
}
