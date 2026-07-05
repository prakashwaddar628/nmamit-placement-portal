package com.nmamit.placement_backend.Application.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApplicationRequest {
    
    @NotNull
    private Long jobDriveId;
}
