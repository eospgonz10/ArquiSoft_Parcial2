package com.udea.ArquiSoft_Parcial2.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AlmacenDTO {
    
    private Long id;
    
    @NotBlank(message = "El nombre del almacén es obligatorio")
    @Size(max = 100, message = "El nombre no puede exceder 100 caracteres")
    private String nombre;
    
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255, message = "La dirección no puede exceder 255 caracteres")
    private String direccion;
    
    @Size(max = 20, message = "El teléfono no puede exceder 20 caracteres")
    private String telefono;
    
    @Size(max = 100, message = "El email no puede exceder 100 caracteres")
    private String email;
    
    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 50, message = "La ciudad no puede exceder 50 caracteres")
    private String ciudad;
    
    @Size(max = 50, message = "El país no puede exceder 50 caracteres")
    private String pais;
    
    private Boolean activo;
    
    private LocalDateTime fechaCreacion;
    
    private LocalDateTime fechaActualizacion;
    
    // Lista de productos (solo para respuestas, no para creación)
    private List<ProductoDTO> productos;
    
    private Integer totalProductos;
    
    private Integer totalStock;
}
