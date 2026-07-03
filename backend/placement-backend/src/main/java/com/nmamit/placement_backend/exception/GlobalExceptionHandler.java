package com.nmamit.placement_backend.exception;

import com.nmamit.placement_backend.common.ApiResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

// provides centralized exception handling for all REST controllers. It eliminates duplicate try-catch blocks, improves maintainability, and ensures consistent error responses across the application
@RestControllerAdvice
public class GlobalExceptionHandler {
    
    @ExceptionHandler(EmailAlreadyExistsException.class)
    public ResponseEntity<ApiResponse<Void>> handleEmailAlreadyExistsException(
        EmailAlreadyExistsException ex) {

            ApiResponse<Void> response = ApiResponse.<Void> builder()
            .success(false)
            .message(ex.getMessage())
            .data(null)
            .build();

        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);

    }
}
