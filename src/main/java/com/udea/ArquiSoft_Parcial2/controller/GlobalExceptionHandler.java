package com.udea.ArquiSoft_Parcial2.controller;

import com.udea.ArquiSoft_Parcial2.dto.ApiResponse;
import com.udea.ArquiSoft_Parcial2.exception.AlmacenNoEncontradoException;
import com.udea.ArquiSoft_Parcial2.exception.ProductoDuplicadoException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * Manejador global de excepciones para la API
 * Proporciona respuestas consistentes de error en formato JSON
 */
@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {
    
    /**
     * Maneja excepciones de almacén no encontrado
     */
    @ExceptionHandler(AlmacenNoEncontradoException.class)
    public ResponseEntity<ApiResponse<Object>> handleAlmacenNoEncontrado(
            AlmacenNoEncontradoException ex) {
        
        log.warn("Almacén no encontrado: {}", ex.getMessage());
        
        ApiResponse<Object> response = ApiResponse.error(
            ex.getMessage(), 
            "v1", 
            HttpStatus.NOT_FOUND.value()
        );
        
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(response);
    }
    
    /**
     * Maneja excepciones de producto duplicado
     */
    @ExceptionHandler(ProductoDuplicadoException.class)
    public ResponseEntity<ApiResponse<Object>> handleProductoDuplicado(
            ProductoDuplicadoException ex) {
        
        log.warn("Producto duplicado: {}", ex.getMessage());
        
        ApiResponse<Object> response = ApiResponse.error(
            ex.getMessage(), 
            "v1", 
            HttpStatus.CONFLICT.value()
        );
        
        return ResponseEntity.status(HttpStatus.CONFLICT).body(response);
    }
    
    /**
     * Maneja errores de validación de campos
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponse<Map<String, String>>> handleValidationErrors(
            MethodArgumentNotValidException ex) {
        
        log.warn("Errores de validación encontrados");
        
        Map<String, String> errors = new HashMap<>();
        ex.getBindingResult().getAllErrors().forEach((error) -> {
            String fieldName = ((FieldError) error).getField();
            String errorMessage = error.getDefaultMessage();
            errors.put(fieldName, errorMessage);
        });
        
        ApiResponse<Map<String, String>> response = ApiResponse.<Map<String, String>>builder()
                .success(false)
                .message("Errores de validación en los datos enviados")
                .data(errors)
                .version("v1")
                .statusCode(HttpStatus.BAD_REQUEST.value())
                .build();
        
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(response);
    }
    
    /**
     * Maneja cualquier otra excepción no controlada
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponse<Object>> handleGenericException(Exception ex) {
        
        log.error("Error interno del servidor", ex);
        
        ApiResponse<Object> response = ApiResponse.error(
            "Error interno del servidor", 
            "v1", 
            HttpStatus.INTERNAL_SERVER_ERROR.value()
        );
        
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(response);
    }
}
