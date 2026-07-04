package com.nmamit.placement_backend.company.dto.response;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CompanyResponse {

    private Long id;

    private String companyName;

    private String website;

    private String industry;

    private String description;

    private String logoUrl;

    private Boolean active;
}