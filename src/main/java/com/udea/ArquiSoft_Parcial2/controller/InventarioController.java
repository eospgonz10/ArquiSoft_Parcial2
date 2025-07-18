package com.udea.ArquiSoft_Parcial2.controller;

import com.udea.ArquiSoft_Parcial2.dto.ApiResponse;
import com.udea.ArquiSoft_Parcial2.dto.ProductoDTO;
import com.udea.ArquiSoft_Parcial2.service.ProductoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@RequestMapping("/inventario")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Inventario", description = "API para gestión de inventarios de productos en almacenes")
public class InventarioController {
    
    private final ProductoService productoService;
    
    /**
     * GET: Consulta el inventario de productos de un almacén determinado
     */
    @Operation(summary = "Consultar inventario por almacén", 
               description = "Obtiene la lista de productos disponibles en un almacén específico")
    @GetMapping("/productos")
    public ResponseEntity<ApiResponse<List<ProductoDTO>>> consultarInventario(
            @Parameter(description = "ID del almacén", required = true) 
            @RequestParam("almacenId") Long almacenId,
            @RequestHeader(value = "API-Version", defaultValue = "v1") String version) {
        
        log.info("GET /api/inventario/productos - Almacén ID: {}", almacenId);
        
        try {
            List<ProductoDTO> productos = productoService.consultarInventarioPorAlmacen(almacenId);
            ApiResponse<List<ProductoDTO>> response = ApiResponse.success(
                productos, "Inventario consultado exitosamente", version);
            
            return ResponseEntity.ok(response);
            
        } catch (Exception e) {
            log.error("Error al consultar inventario para almacén ID: {}", almacenId, e);
            ApiResponse<List<ProductoDTO>> errorResponse = ApiResponse.error(
                e.getMessage(), version, HttpStatus.NOT_FOUND.value());
            
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponse);
        }
    }
    
    /**
     * POST: Crea un nuevo producto con cantidad inicial en stock
     */
    @Operation(summary = "Crear nuevo producto", 
               description = "Registra un nuevo producto en el inventario con información completa")
    @PostMapping("/productos")
    public ResponseEntity<ApiResponse<ProductoDTO>> crearProducto(
            @Parameter(description = "Información del producto a crear", required = true)
            @Valid @RequestBody ProductoDTO productoDTO,
            @RequestHeader(value = "API-Version", defaultValue = "v1") String version) {
        
        log.info("POST /api/inventario/productos - Código: {}", productoDTO.getCodigo());
        
        try {
            ProductoDTO productoCreado = productoService.crearProducto(productoDTO);
            ApiResponse<ProductoDTO> response = ApiResponse.success(
                productoCreado, "Producto creado exitosamente", version);
            
            return ResponseEntity.status(HttpStatus.CREATED).body(response);
            
        } catch (Exception e) {
            log.error("Error al crear producto con código: {}", productoDTO.getCodigo(), e);
            ApiResponse<ProductoDTO> errorResponse = ApiResponse.error(
                e.getMessage(), version, HttpStatus.BAD_REQUEST.value());
            
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorResponse);
        }
    }
}
