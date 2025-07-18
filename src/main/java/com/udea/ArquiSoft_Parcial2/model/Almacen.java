package com.udea.ArquiSoft_Parcial2.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "almacenes")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Almacen {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @NotBlank(message = "El nombre del almacén es obligatorio")
    @Size(max = 100)
    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;
    
    @NotBlank(message = "La dirección es obligatoria")
    @Size(max = 255)
    @Column(name = "direccion", nullable = false)
    private String direccion;
    
    @Size(max = 20)
    @Column(name = "telefono", length = 20)
    private String telefono;
    
    @Size(max = 100)
    @Column(name = "email", length = 100)
    private String email;
    
    @NotBlank(message = "La ciudad es obligatoria")
    @Size(max = 50)
    @Column(name = "ciudad", nullable = false, length = 50)
    private String ciudad;
    
    @Size(max = 50)
    @Column(name = "pais", length = 50)
    private String pais;
    
    @Column(name = "activo")
    @Builder.Default
    private Boolean activo = true;
    
    @Column(name = "fecha_creacion", updatable = false)
    private LocalDateTime fechaCreacion;
    
    @Column(name = "fecha_actualizacion")
    private LocalDateTime fechaActualizacion;
    
    // Relación uno a muchos con productos
    @OneToMany(mappedBy = "almacen", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Producto> productos;
    
    @PrePersist
    protected void onCreate() {
        fechaCreacion = LocalDateTime.now();
        fechaActualizacion = LocalDateTime.now();
    }
    
    @PreUpdate
    protected void onUpdate() {
        fechaActualizacion = LocalDateTime.now();
    }
}
