package com.udea.ArquiSoft_Parcial2.repository;

import com.udea.ArquiSoft_Parcial2.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ProductoRepository extends JpaRepository<Producto, Long> {
    
    @Query("SELECT p FROM Producto p WHERE p.almacen.id = :almacenId AND p.activo = true ORDER BY p.nombre")
    List<Producto> findByAlmacenIdAndActivoTrue(@Param("almacenId") Long almacenId);
    
    boolean existsByCodigoIgnoreCase(String codigo);
}
