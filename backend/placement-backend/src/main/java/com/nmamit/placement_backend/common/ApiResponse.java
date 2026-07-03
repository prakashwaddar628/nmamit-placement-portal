package com.nmamit.placement_backend.common;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
@AllArgsConstructor
public class ApiResponse<T> {
    
    private boolean success;

    private String message;

    private T data;
}
