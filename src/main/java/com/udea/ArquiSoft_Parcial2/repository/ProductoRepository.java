package com.udea.ArquiSoft_Parcial2.repository;

import com.udea.ArquiSoft_Parcial2.model.Producto;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductoRepository extends JpaRepository<Producto, Long> {
}
