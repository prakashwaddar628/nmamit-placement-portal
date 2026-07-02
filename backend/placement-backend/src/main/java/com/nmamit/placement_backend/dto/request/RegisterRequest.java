package com.nmamit.placement_backend.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "College email is required")
    @Email(message = "Invalid email format")
    @Pattern(
        regexp = "^[a-zA-Z0-9._%+-]+@nmamit\\.ac\\.in$",
        message = "College email must be a valid NMAMIT email address"
    )
    private String collegeEmail;

    @NotBlank(message = "Password is required")
    private String password;
}
