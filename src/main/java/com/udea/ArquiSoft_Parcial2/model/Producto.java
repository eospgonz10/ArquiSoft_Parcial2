package com.udea.ArquiSoft_Parcial2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "productos")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Producto {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El código del producto es obligatorio")
    @Size(max = 50)
    @Column(name = "codigo", nullable = false, unique = true, length = 50)
    private String codigo;
    
    @NotBlank(message = "El nombre del producto es obligatorio")
    @Size(max = 100)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @Size(max = 500)
    @Column(name = "descripcion", length = 500)
    private String descripcion;
    
    @NotNull(message = "El precio es obligatorio")
    @DecimalMin(value = "0.0", inclusive = false, message = "El precio debe ser mayor a 0")
    @Digits(integer = 10, fraction = 2)
    @Column(name = "precio", nullable = false, precision = 12, scale = 2)
    private BigDecimal precio;
    
    @NotNull(message = "La cantidad en stock es obligatoria")
    @Min(value = 0, message = "La cantidad en stock no puede ser negativa")
    @Column(name = "cantidad_stock", nullable = false)
    private Integer cantidadStock;
    
    @Min(value = 0, message = "La cantidad mínima no puede ser negativa")
    @Column(name = "cantidad_minima")
    @Builder.Default
    private Integer cantidadMinima = 0;
    
    @Min(value = 0, message = "La cantidad máxima no puede ser negativa")
    @Column(name = "cantidad_maxima")
    private Integer cantidadMaxima;
    
    @Size(max = 50, message = "La categoría no puede exceder 50 caracteres")
    @Column(name = "categoria", length = 50)
    private String categoria;
    
    @Size(max = 20, message = "La unidad de medida no puede exceder 20 caracteres")
    @Column(name = "unidad_medida", length = 20)
    private String unidadMedida;
    
    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    @Column(name = "fecha_vencimiento")
    private LocalDateTime fechaVencimiento;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "almacen_id", nullable = false)
    @NotNull(message = "El almacén es obligatorio")
    private Almacen almacen;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
    
    public boolean isStockCritico() {
        return cantidadStock <= cantidadMinima;
    }
    
    public boolean isVencido() {
        return fechaVencimiento != null && fechaVencimiento.isBefore(LocalDateTime.now());
    }
}
