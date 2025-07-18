package com.udea.ArquiSoft_Parcial2.service;

import com.udea.ArquiSoft_Parcial2.dto.AlmacenDTO;
import com.udea.ArquiSoft_Parcial2.exception.AlmacenNoEncontradoException;
import com.udea.ArquiSoft_Parcial2.model.Almacen;
import com.udea.ArquiSoft_Parcial2.repository.AlmacenRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
@Transactional(readOnly = true)
public class AlmacenService {
    
    private final AlmacenRepository almacenRepository;
    
    /**
     * Valida que el almacén existe y está activo
     * @param almacenId ID del almacén
     * @return Almacen si existe y está activo
     * @throws AlmacenNoEncontradoException si no existe o no está activo
     */
    public Almacen validarAlmacenActivo(Long almacenId) {
        log.debug("Validando almacén activo con ID: {}", almacenId);
        
        Optional<Almacen> almacen = almacenRepository.findByIdAndActivoTrue(almacenId);
        
        if (almacen.isEmpty()) {
            log.warn("Almacén no encontrado o inactivo con ID: {}", almacenId);
            throw new AlmacenNoEncontradoException("Almacén no encontrado o inactivo con ID: " + almacenId);
        }
        
        log.debug("Almacén validado exitosamente: {}", almacen.get().getNombre());
        return almacen.get();
    }
    
    /**
     * Convierte una entidad Almacen a DTO
     * @param almacen Entidad almacén
     * @return AlmacenDTO
     */
    public AlmacenDTO convertToDTO(Almacen almacen) {
        return AlmacenDTO.builder()
                .id(almacen.getId())
                .nombre(almacen.getNombre())
                .direccion(almacen.getDireccion())
                .telefono(almacen.getTelefono())
                .email(almacen.getEmail())
                .ciudad(almacen.getCiudad())
                .pais(almacen.getPais())
                .activo(almacen.getActivo())
                .fechaCreacion(almacen.getFechaCreacion())
                .fechaActualizacion(almacen.getFechaActualizacion())
                .build();
    }
}
