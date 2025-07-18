package com.udea.ArquiSoft_Parcial2.config;

import com.udea.ArquiSoft_Parcial2.model.Almacen;
import com.udea.ArquiSoft_Parcial2.model.Producto;
import com.udea.ArquiSoft_Parcial2.repository.AlmacenRepository;
import com.udea.ArquiSoft_Parcial2.repository.ProductoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Inicializador de datos por defecto para la aplicación
 * Carga almacenes y productos de ejemplo al inicio
 */
@Component
@RequiredArgsConstructor
@Slf4j
public class DataInitializer implements ApplicationRunner {
    
    private final AlmacenRepository almacenRepository;
    private final ProductoRepository productoRepository;
    
    @Override
    @Transactional
    public void run(ApplicationArguments args) throws Exception {
        log.info("🔄 Iniciando carga de datos por defecto...");
        
        // Solo cargar datos si no existen almacenes
        if (almacenRepository.count() == 0) {
            cargarAlmacenesYProductos();
            log.info("✅ Datos por defecto cargados exitosamente");
        } else {
            log.info("📊 Ya existen datos en la base de datos, omitiendo carga inicial");
        }
    }
    
    private void cargarAlmacenesYProductos() {
        log.info("📦 Creando almacenes por defecto...");
        
        // Crear almacenes
        Almacen almacenCentral = crearAlmacen(
            "Almacén Central",
            "Calle 10 #20-30",
            "604-1234567",
            "central@empresa.com",
            "Medellín",
            "Colombia"
        );
        
        Almacen almacenNorte = crearAlmacen(
            "Almacén Norte",
            "Carrera 50 #80-20",
            "604-7654321",
            "norte@empresa.com",
            "Medellín",
            "Colombia"
        );
        
        Almacen almacenBogota = crearAlmacen(
            "Almacén Bogotá",
            "Avenida 68 #40-50",
            "601-9876543",
            "bogota@empresa.com",
            "Bogotá",
            "Colombia"
        );
        
        log.info("✅ {} almacenes creados", 3);
        log.info("🛒 Creando productos por defecto...");
        
        // Productos para Almacén Central
        crearProducto("ELEC001", "Laptop Dell", "Laptop Dell Inspiron 15", 
                     new BigDecimal("2500000.00"), 10, 2, 50, 
                     "Electrónicos", "Unidad", almacenCentral);
        
        crearProducto("ELEC002", "Mouse Inalámbrico", "Mouse inalámbrico Logitech", 
                     new BigDecimal("80000.00"), 25, 5, 100, 
                     "Electrónicos", "Unidad", almacenCentral);
        
        crearProducto("OFIC001", "Resma Papel A4", "Resma papel bond A4 75gr", 
                     new BigDecimal("15000.00"), 100, 20, 500, 
                     "Oficina", "Resma", almacenCentral);
        
        // Productos para Almacén Norte  
        crearProducto("ELEC003", "Teclado Mecánico", "Teclado mecánico RGB", 
                     new BigDecimal("350000.00"), 5, 1, 20, 
                     "Electrónicos", "Unidad", almacenNorte);
        
        crearProducto("OFIC002", "Marcadores", "Set marcadores colores", 
                     new BigDecimal("25000.00"), 50, 10, 200, 
                     "Oficina", "Set", almacenNorte);
        
        // Productos para Almacén Bogotá
        crearProducto("ELEC004", "Monitor 24 pulgadas", "Monitor LED Full HD", 
                     new BigDecimal("450000.00"), 8, 2, 30, 
                     "Electrónicos", "Unidad", almacenBogota);
        
        crearProducto("OFIC003", "Cuaderno Universitario", "Cuaderno 100 hojas", 
                     new BigDecimal("8500.00"), 200, 50, 1000, 
                     "Oficina", "Unidad", almacenBogota);
        
        // Producto con stock crítico
        crearProducto("ELEC005", "Cable USB-C", "Cable USB-C 2 metros", 
                     new BigDecimal("35000.00"), 2, 5, 50, 
                     "Electrónicos", "Unidad", almacenCentral);
        
        log.info("✅ {} productos creados", 8);
    }
    
    private Almacen crearAlmacen(String nombre, String direccion, String telefono, 
                               String email, String ciudad, String pais) {
        Almacen almacen = Almacen.builder()
                .nombre(nombre)
                .direccion(direccion)
                .telefono(telefono)
                .email(email)
                .ciudad(ciudad)
                .pais(pais)
                .activo(true)
                .build();
        
        Almacen almacenGuardado = almacenRepository.save(almacen);
        log.debug("📦 Almacén creado: {} (ID: {})", nombre, almacenGuardado.getId());
        
        return almacenGuardado;
    }
    
    private Producto crearProducto(String codigo, String nombre, String descripcion,
                                 BigDecimal precio, Integer stock, Integer stockMin, Integer stockMax,
                                 String categoria, String unidadMedida, Almacen almacen) {
        
        Producto producto = Producto.builder()
                .codigo(codigo)
                .nombre(nombre)
                .descripcion(descripcion)
                .precio(precio)
                .cantidadStock(stock)
                .cantidadMinima(stockMin)
                .cantidadMaxima(stockMax)
                .categoria(categoria)
                .unidadMedida(unidadMedida)
                .almacen(almacen)
                .activo(true)
                .build();
        
        Producto productoGuardado = productoRepository.save(producto);
        log.debug("🛒 Producto creado: {} (ID: {}) - Stock: {} en {}", 
                 nombre, productoGuardado.getId(), stock, almacen.getNombre());
        
        return productoGuardado;
    }
}
