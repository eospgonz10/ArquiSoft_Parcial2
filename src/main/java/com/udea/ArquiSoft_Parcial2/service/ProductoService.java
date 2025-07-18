package com.udea.ArquiSoft_Parcial2.service;

import com.udea.ArquiSoft_Parcial2.dto.ProductoDTO;
import com.udea.ArquiSoft_Parcial2.exception.ProductoDuplicadoException;
import com.udea.ArquiSoft_Parcial2.model.Almacen;
import com.udea.ArquiSoft_Parcial2.model.Producto;
import com.udea.ArquiSoft_Parcial2.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class ProductoService {
    
    private final ProductoRepository productoRepository;
    private final AlmacenService almacenService;
    
    /**
     * MÉTODO GET: Consulta el inventario de productos de un almacén determinado
     * @param almacenId ID del almacén
     * @return Lista de ProductoDTO del inventario
     */
    public List<ProductoDTO> consultarInventarioPorAlmacen(Long almacenId) {
        log.info("Consultando inventario para almacén ID: {}", almacenId);
        
        // Validar que el almacén existe y está activo
        Almacen almacen = almacenService.validarAlmacenActivo(almacenId);
        
        // Obtener productos del almacén
        List<Producto> productos = productoRepository.findByAlmacenIdAndActivoTrue(almacenId);
        
        log.info("Se encontraron {} productos en el almacén: {}", productos.size(), almacen.getNombre());
        
        // Convertir a DTOs
        return productos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }
    
    /**
     * MÉTODO POST: Ingresa la información de un producto con su cantidad inicial en stock
     * @param productoDTO Datos del producto a crear
     * @return ProductoDTO del producto creado
     */
    @Transactional
    public ProductoDTO crearProducto(ProductoDTO productoDTO) {
        log.info("Creando nuevo producto con código: {}", productoDTO.getCodigo());
        
        // Validar que el almacén existe y está activo
        Almacen almacen = almacenService.validarAlmacenActivo(productoDTO.getAlmacenId());
        
        // Validar que el código del producto no exista
        if (productoRepository.existsByCodigoIgnoreCase(productoDTO.getCodigo())) {
            log.warn("El código de producto ya existe: {}", productoDTO.getCodigo());
            throw new ProductoDuplicadoException("Ya existe un producto con el código: " + productoDTO.getCodigo());
        }
        
        // Convertir DTO a entidad
        Producto producto = convertToEntity(productoDTO, almacen);
        
        // Guardar el producto
        Producto productoGuardado = productoRepository.save(producto);
        
        log.info("Producto creado exitosamente con ID: {} en almacén: {}", 
                productoGuardado.getId(), almacen.getNombre());
        
        // Convertir y retornar DTO
        return convertToDTO(productoGuardado);
    }
    
    /**
     * Convierte una entidad Producto a DTO
     * @param producto Entidad producto
     * @return ProductoDTO
     */
    public ProductoDTO convertToDTO(Producto producto) {
        return ProductoDTO.builder()
                .id(producto.getId())
                .codigo(producto.getCodigo())
                .nombre(producto.getNombre())
                .descripcion(producto.getDescripcion())
                .precio(producto.getPrecio())
                .cantidadStock(producto.getCantidadStock())
                .cantidadMinima(producto.getCantidadMinima())
                .cantidadMaxima(producto.getCantidadMaxima())
                .categoria(producto.getCategoria())
                .unidadMedida(producto.getUnidadMedida())
                .activo(producto.getActivo())
                .fechaCreacion(producto.getFechaCreacion())
                .fechaActualizacion(producto.getFechaActualizacion())
                .fechaVencimiento(producto.getFechaVencimiento())
                .almacenId(producto.getAlmacen().getId())
                .almacenNombre(producto.getAlmacen().getNombre())
                .stockCritico(producto.isStockCritico())
                .vencido(producto.isVencido())
                .build();
    }
    
    /**
     * Convierte un DTO a entidad Producto
     * @param productoDTO DTO del producto
     * @param almacen Entidad del almacén
     * @return Producto
     */
    private Producto convertToEntity(ProductoDTO productoDTO, Almacen almacen) {
        return Producto.builder()
                .codigo(productoDTO.getCodigo())
                .nombre(productoDTO.getNombre())
                .descripcion(productoDTO.getDescripcion())
                .precio(productoDTO.getPrecio())
                .cantidadStock(productoDTO.getCantidadStock())
                .cantidadMinima(productoDTO.getCantidadMinima() != null ? productoDTO.getCantidadMinima() : 0)
                .cantidadMaxima(productoDTO.getCantidadMaxima())
                .categoria(productoDTO.getCategoria())
                .unidadMedida(productoDTO.getUnidadMedida())
                .fechaVencimiento(productoDTO.getFechaVencimiento())
                .almacen(almacen)
                .activo(true) // Por defecto activo
                .build();
    }
}
