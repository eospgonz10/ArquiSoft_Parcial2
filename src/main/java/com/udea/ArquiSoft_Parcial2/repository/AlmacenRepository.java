package com.udea.ArquiSoft_Parcial2.repository;

import com.udea.ArquiSoft_Parcial2.model.Almacen;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AlmacenRepository extends JpaRepository<Almacen, Long> {
    
    // Para validar que el almacén existe y está activo antes de consultar o crear productos
    Optional<Almacen> findByIdAndActivoTrue(Long id);
}
