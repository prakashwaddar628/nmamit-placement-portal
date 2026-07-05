package com.nmamit.placement_backend.company;

import com.nmamit.placement_backend.common.exception.BadRequestException;
import com.nmamit.placement_backend.common.exception.ResourceNotFoundException;
import com.nmamit.placement_backend.common.mapper.CompanyMapper;
import com.nmamit.placement_backend.company.dto.request.CompanyRequest;
import com.nmamit.placement_backend.company.dto.response.CompanyResponse;
import com.nmamit.placement_backend.company.entity.Company;
import com.nmamit.placement_backend.company.repository.CompanyRepository;
import com.nmamit.placement_backend.company.service.impl.CompanyServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("CompanyServiceImpl Tests")
class CompanyServiceTest {

    @Mock private CompanyRepository companyRepository;
    @Mock private CompanyMapper companyMapper;

    @InjectMocks private CompanyServiceImpl companyService;

    @Test
    @DisplayName("Should create company successfully")
    void shouldCreateCompany() {
        CompanyRequest request = new CompanyRequest();
        request.setCompanyName("Infosys");
        request.setWebsite("https://infosys.com");
        request.setIndustry("IT");

        Company company = Company.builder().id(1L).companyName("Infosys").active(true).build();
        CompanyResponse response = CompanyResponse.builder().id(1L).companyName("Infosys").build();

        when(companyRepository.existsByCompanyNameIgnoreCase("Infosys")).thenReturn(false);
        when(companyRepository.save(any(Company.class))).thenReturn(company);
        when(companyMapper.toResponse(company)).thenReturn(response);

        CompanyResponse result = companyService.createCompany(request);

        assertThat(result.getCompanyName()).isEqualTo("Infosys");
        verify(companyRepository).save(any(Company.class));
    }

    @Test
    @DisplayName("Should throw BadRequestException when company already exists")
    void shouldThrowWhenCompanyExists() {
        CompanyRequest request = new CompanyRequest();
        request.setCompanyName("Infosys");

        when(companyRepository.existsByCompanyNameIgnoreCase("Infosys")).thenReturn(true);

        assertThatThrownBy(() -> companyService.createCompany(request))
                .isInstanceOf(BadRequestException.class)
                .hasMessageContaining("already exists");
    }

    @Test
    @DisplayName("Should throw ResourceNotFoundException when company not found")
    void shouldThrowWhenCompanyNotFound() {
        when(companyRepository.findById(999L)).thenReturn(Optional.empty());

        assertThatThrownBy(() -> companyService.getCompanyById(999L))
                .isInstanceOf(ResourceNotFoundException.class);
    }

    @Test
    @DisplayName("Should soft-delete company (set active=false)")
    void shouldSoftDeleteCompany() {
        Company company = Company.builder().id(1L).companyName("Infosys").active(true).build();
        when(companyRepository.findById(1L)).thenReturn(Optional.of(company));
        when(companyRepository.save(any(Company.class))).thenReturn(company);

        companyService.deleteCompany(1L);

        assertThat(company.getActive()).isFalse();
        verify(companyRepository).save(company);
    }
}
