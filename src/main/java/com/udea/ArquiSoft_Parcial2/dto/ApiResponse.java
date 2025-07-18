package com.udea.ArquiSoft_Parcial2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ApiResponse<T> {
    
    private boolean success;
    
    private String message;
    
    private T data;
    
    private String version;
    
    private LocalDateTime timestamp;
    
    private String path;
    
    private Integer statusCode;
    
    public static <T> ApiResponse<T> success(T data, String message, String version) {
        return ApiResponse.<T>builder()
                .success(true)
                .message(message)
                .data(data)
                .version(version)
                .timestamp(LocalDateTime.now())
                .statusCode(200)
                .build();
    }
    
    public static <T> ApiResponse<T> error(String message, String version, Integer statusCode) {
        return ApiResponse.<T>builder()
                .success(false)
                .message(message)
                .version(version)
                .timestamp(LocalDateTime.now())
                .statusCode(statusCode)
                .build();
    }
}
