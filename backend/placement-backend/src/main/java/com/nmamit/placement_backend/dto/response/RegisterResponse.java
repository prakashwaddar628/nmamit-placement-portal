package com.nmamit.placement_backend.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor

public class RegisterResponse {
    private String message;

    private String collegeEmail;
}
