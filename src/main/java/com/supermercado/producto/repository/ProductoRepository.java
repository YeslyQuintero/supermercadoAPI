package com.supermercado.producto.repository;

import com.supermercado.producto.entity.Producto;


import java.util.List;
import java.util.Optional;

public interface ProductoRepository {
    Optional<Producto> findById(Long id);

    Producto save(Producto producto);

    List<Producto> findAll();

    void delete(Producto producto);
}
