package com.nmamit.placement_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
    
    @NotBlank(message = "College email is required")
    @Email(message = "Invalid email")
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@nmamit\\.in$", 
        message = "Email must be a valid nmamit.in email address"
    )
    private String CollegeEmail;

    @NotBlank(message = "Password is required")
    private String password;
}
