package com.nmamit.placement_backend.Application.dto.request;

import jakarta.validation.constraints.NotNull;

import com.nmamit.placement_backend.enums.ApplicationStatus;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UpdateApplicationStatusRequest {

    @NotNull
    private ApplicationStatus status;

}