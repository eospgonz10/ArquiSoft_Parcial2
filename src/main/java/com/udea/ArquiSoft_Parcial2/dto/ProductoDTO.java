package com.udea.ArquiSoft_Parcial2.dto;

import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductoDTO {
    
    private Long id;
    
    @NotBlank(message = "El código del producto es obligatorio")
    @Size(max = 50, message = "El código no puede exceder 50 caracteres")
    private String codigo;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    private String descripcion;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    private BigDecimal precio;
    
    @NotNull(message = "La cantidad en stock es obligatoria")
    @Min(value = 0, message = "La cantidad en stock no puede ser negativa")
    private Integer cantidadStock;
    
    @Min(value = 0, message = "La cantidad mínima no puede ser negativa")
    private Integer cantidadMinima;
    
    @Min(value = 0, message = "La cantidad máxima no puede ser negativa")
    private Integer cantidadMaxima;
    
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    private String categoria;
    
    @Size(max = 20, message = "La unidad de medida no puede exceder 20 caracteres")
    private String unidadMedida;
    
    private Boolean activo;
    
    private LocalDateTime fechaCreacion;
    
    private LocalDateTime fechaActualizacion;
    
    private LocalDateTime fechaVencimiento;
    
    @NotNull(message = "El ID del almacén es obligatorio")
    private Long almacenId;
    
    private String almacenNombre;
    
    private Boolean stockCritico;
    
    private Boolean vencido;
}
