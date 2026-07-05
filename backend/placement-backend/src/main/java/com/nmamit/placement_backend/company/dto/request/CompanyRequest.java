package com.nmamit.placement_backend.company.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequest {

    @NotBlank(message = "Company name is required")
    @Size(min = 2, max = 200, message = "Company name must be between 2 and 200 characters")
    private String companyName;

    private String website;

    @NotBlank(message = "Industry is required")
    private String industry;

    @Size(max = 2000, message = "Description cannot exceed 2000 characters")
    private String description;

    private String logoUrl;

    private Boolean active;
}