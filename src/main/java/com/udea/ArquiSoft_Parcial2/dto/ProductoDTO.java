package com.udea.ArquiSoft_Parcial2.dto;

import io.swagger.v3.oas.annotations.media.Schema;
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
@Schema(description = "Datos del producto en el inventario")
public class ProductoDTO {
    
    @Schema(description = "ID único del producto", example = "1")
    private Long id;
    
    @NotBlank(message = "El código del producto es obligatorio")
    @Size(max = 50, message = "El código no puede exceder 50 caracteres")
    @Schema(description = "Código único del producto", example = "ELEC001", required = true)
    private String codigo;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    @Schema(description = "Nombre del producto", example = "Laptop Dell", required = true)
    private String nombre;
    
    @Size(max = 500, message = "La descripción no puede exceder 500 caracteres")
    @Schema(description = "Descripción del producto", example = "Laptop Dell Inspiron 15")
    private String descripcion;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Schema(description = "Precio unitario", example = "2500000.00", required = true)
    private BigDecimal precio;
    
    @NotNull(message = "La cantidad en stock es obligatoria")
    @Min(value = 0, message = "La cantidad en stock no puede ser negativa")
    @Schema(description = "Cantidad actual en stock", example = "10", required = true)
    private Integer cantidadStock;
    
    @Min(value = 0, message = "La cantidad mínima no puede ser negativa")
    @Schema(description = "Stock mínimo requerido", example = "2")
    private Integer cantidadMinima;
    
    @Min(value = 0, message = "La cantidad máxima no puede ser negativa")
    @Schema(description = "Stock máximo permitido", example = "50")
    private Integer cantidadMaxima;
    
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    @Schema(description = "Categoría del producto", example = "Electrónicos")
    private String categoria;
    
    @Size(max = 20, message = "La unidad de medida no puede exceder 20 caracteres")
    @Schema(description = "Unidad de medida", example = "Unidad")
    private String unidadMedida;
    
    @Schema(description = "Estado del producto", example = "true")
    private Boolean activo;
    
    @Schema(description = "Fecha de creación")
    private LocalDateTime fechaCreacion;
    
    @Schema(description = "Fecha de última actualización")
    private LocalDateTime fechaActualizacion;
    
    @Schema(description = "Fecha de vencimiento")
    private LocalDateTime fechaVencimiento;
    
    @NotNull(message = "El ID del almacén es obligatorio")
    @Schema(description = "ID del almacén", example = "1", required = true)
    private Long almacenId;
    
    @Schema(description = "Nombre del almacén", example = "Almacén Central")
    private String almacenNombre;
    
    @Schema(description = "Indica si el stock está en nivel crítico", example = "false")
    private Boolean stockCritico;
    
    @Schema(description = "Indica si el producto está vencido", example = "false")
    private Boolean vencido;
}
