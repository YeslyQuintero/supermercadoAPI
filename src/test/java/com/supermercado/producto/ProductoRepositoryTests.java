package com.supermercado.producto;

import com.supermercado.producto.entity.Producto;
import com.supermercado.producto.repository.ProductoRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;

public class ProductoRepositoryTests {

    @Autowired
    private TestEntityManager entityManager;

    @Autowired
    private ProductoRepository repository;

    @Test
    public void testCreateProducto(){
        Producto producto = new Producto();
        producto.setColor("rojo");
        producto.setTalla("l");
        producto.setDiseño("marvel");
        producto.setSensacion("pana");
        producto.setPrecio("36900");

        Producto savedProducto = repository.save(producto);

        Producto existProducto = entityManager.find(Producto.class, savedProducto.getColor());
    }
}
