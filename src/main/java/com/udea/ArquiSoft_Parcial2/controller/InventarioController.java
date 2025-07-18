package com.udea.ArquiSoft_Parcial2.controller;

import com.udea.ArquiSoft_Parcial2.dto.ApiResponse;
import com.udea.ArquiSoft_Parcial2.dto.ProductoDTO;
import com.udea.ArquiSoft_Parcial2.service.ProductoService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controlador REST para operaciones de inventario de productos
 */
@RestController
@RequestMapping("/api/inventario")
@RequiredArgsConstructor
@Slf4j
public class InventarioController {
    
    private final ProductoService productoService;
    
    /**
     * GET: Consulta el inventario de productos de un almacén determinado
     * 
     * @param almacenId ID del almacén (parámetro de consulta)
     * @param version Versión de la API
     * @return Lista de productos del inventario
     */
    @GetMapping("/productos")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> consultarInventario(
            @RequestParam("almacenId") Long almacenId,
            @RequestHeader(value = "API-Version", defaultValue = "v1") String version) {
        
        log.info("GET /api/inventario/productos - Almacén ID: {}, Versión: {}", almacenId, version);
        
        try {
            // Consultar inventario por almacén
            List<ProductoDTO> productos = productoService.consultarInventarioPorAlmacen(almacenId);
            
            // Crear respuesta exitosa
            ApiResponse<List<ProductoDTO>> response = ApiResponse.success(
                productos, 
                "Inventario consultado exitosamente", 
                version
            );
            
            log.info("Inventario consultado exitosamente para almacén ID: {}. {} productos encontrados", 
                    almacenId, productos.size());
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error al consultar inventario para almacén ID: {}", almacenId, e);
            
            ApiResponse<List<ProductoDTO>> errorResponse = ApiResponse.error(
                e.getMessage(), 
                version, 
                HttpStatus.NOT_FOUND.value()
            );
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    
    /**
     * POST: Ingresa la información de un producto con su cantidad inicial en stock
     * 
     * @param productoDTO Datos del producto a crear
     * @param version Versión de la API
     * @return Producto creado
     */
    @PostMapping("/productos")
    public ResponseEntity<ApiResponse<ProductoDTO>> crearProducto(
            @Valid @RequestBody ProductoDTO productoDTO,
            @RequestHeader(value = "API-Version", defaultValue = "v1") String version) {
        
        log.info("POST /api/inventario/productos - Código: {}, Versión: {}", 
                productoDTO.getCodigo(), version);
        
        try {
            ProductoDTO productoCreado = productoService.crearProducto(productoDTO);
            
            ApiResponse<ProductoDTO> response = ApiResponse.success(
                productoCreado, 
                "Producto creado exitosamente", 
                version
            );
            
            log.info("Producto creado exitosamente con ID: {} y código: {}", 
                    productoCreado.getId(), productoCreado.getCodigo());
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            log.error("Error al crear producto con código: {}", productoDTO.getCodigo(), e);
            
            ApiResponse<ProductoDTO> errorResponse = ApiResponse.error(
                e.getMessage(), 
                version, 
                HttpStatus.BAD_REQUEST.value()
            );
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
