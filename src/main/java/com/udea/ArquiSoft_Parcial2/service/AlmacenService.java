package com.udea.ArquiSoft_Parcial2.service;

import com.udea.ArquiSoft_Parcial2.model.Almacen;
import com.udea.ArquiSoft_Parcial2.model.Producto;
import com.udea.ArquiSoft_Parcial2.repository.AlmacenRepositoy;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AlmacenService {
    private AlmacenRepositoy alamacenRepositoy;

    AlmacenService(AlmacenRepositoy alamacenRepositoy) {
        this.alamacenRepositoy = alamacenRepositoy;
    }

    public List<Almacen> getAllAlmacen() {
        Iterable<Almacen> almacenes = alamacenRepositoy.findAll();
        List<Almacen> almacenesLista = new ArrayList<>();
        if (almacenes != null) {
            almacenes.forEach(item -> almacenesLista.add(item));
        }
        return almacenesLista;
    }

    public Almacen getAlmacenById(Long id) {
        return alamacenRepositoy.findById(id)
                .orElseThrow(()-> new RuntimeException("Almacen no encontrado"));
    }

    public Almacen createAlmacen(Almacen almacen) {
        return alamacenRepositoy.save(almacen);
    }

    public List<Producto> getAllProductosByAlmacenId(Long id) {
        Almacen almacen = getAlmacenById(id);
        List<Producto> inventario = new ArrayList<>();
        if (almacen == null) {
            new RuntimeException("El almacen " + id + "no existe");
            return inventario;
        } else {
            List<Producto> productosEnAlmacen = almacen.getProductos();
            return productosEnAlmacen;
        }
    }

}
