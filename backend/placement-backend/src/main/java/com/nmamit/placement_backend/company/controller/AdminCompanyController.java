package com.nmamit.placement_backend.company.controller;

import com.nmamit.placement_backend.common.ApiResponse;
import com.nmamit.placement_backend.company.dto.request.CompanyRequest;
import com.nmamit.placement_backend.company.dto.response.CompanyResponse;
import com.nmamit.placement_backend.company.service.CompanyService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@Tag(name = "Admin - Companies", description = "Company management for admins")
@SecurityRequirement(name = "bearerAuth")
@RestController
@RequestMapping("/api/admin/companies")
@RequiredArgsConstructor
@PreAuthorize("hasRole('ADMIN')")
public class AdminCompanyController {

    private final CompanyService companyService;

    @Operation(summary = "Create a new company")
    @PostMapping
    public ResponseEntity<ApiResponse<CompanyResponse>> createCompany(
            @Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ApiResponse.success("Company created successfully", companyService.createCompany(request)));
    }

    @Operation(summary = "Update a company")
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<CompanyResponse>> updateCompany(
            @PathVariable Long id,
            @Valid @RequestBody CompanyRequest request) {
        return ResponseEntity.ok(ApiResponse.success("Company updated successfully",
                companyService.updateCompany(id, request)));
    }

    @Operation(summary = "Soft-delete a company")
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteCompany(@PathVariable Long id) {
        companyService.deleteCompany(id);
        return ResponseEntity.ok(ApiResponse.success("Company deleted successfully", null));
    }
}
