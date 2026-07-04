package com.nmamit.placement_backend.company.dto.request;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyRequest {

    private String companyName;

    private String website;

    private String industry;

    private String description;

    private String logoUrl;

    private Boolean active;
}