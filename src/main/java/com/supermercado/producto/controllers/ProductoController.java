package com.supermercado.producto.controllers;

import com.supermercado.producto.entity.Producto;
import com.supermercado.producto.repository.ProductoRepository;
import com.supermercado.producto.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.*;

@RestController

public class ProductoController {
    @Autowired
    private ProductoRepository productoRepository;
    private Message message = new Message();

    @RequestMapping(value = "api/producto/{id}", method = RequestMethod.GET)
    public ResponseEntity<Optional> getProducto(@PathVariable Long id){
        Optional<Producto> foundUser = productoRepository.findById(id);
        if(foundUser.isPresent()){
            return message.viewMessage(HttpStatus.OK,"success", "Producto found");
        }
        return message.viewMessage(HttpStatus.NOT_FOUND, "Not found", "Producto not found");
    }

    @RequestMapping(value = "api/producto", method = RequestMethod.POST)
    public ResponseEntity createProducto(@RequestBody Producto producto){
        Map<String,String> response = new LinkedHashMap<>();
        try{
            producto.setColor(producto.getColor());
            producto.setTalla(producto.getTalla());
            producto.setDiseño(producto.getDiseño());
            producto.setSensacion(producto.getSensacion());
            producto.setPrecio(producto.getPrecio());
            productoRepository.save(producto);
            return message.viewMessage(HttpStatus.OK, "success", "registered producto success!");
        }catch (Exception e){
            return message.viewMessage(HttpStatus.INTERNAL_SERVER_ERROR, "error", "An error occurred while registering the producto!");
        }
    }

    @RequestMapping(value = "api/producto", method = RequestMethod.GET)
    public List<Producto> listProducto(){
        return productoRepository.findAll();

    }

    @RequestMapping(value = "api/producto/{id}", method = RequestMethod.PUT)
    public ResponseEntity editProducto(@RequestBody Producto newProducto, @PathVariable Long id){
        Map<String, String> response = new HashMap<>();
        try{
            Producto producto = productoRepository.findById(id).get();
            producto.setColor(newProducto.getColor());
            producto.setTalla(newProducto.getTalla());
            producto.setDiseño(newProducto.getDiseño());
            producto.setSensacion(newProducto.getSensacion());
            producto.setPrecio(newProducto.getPrecio());
            productoRepository.save(producto);

            return message.viewMessage(HttpStatus.OK, "success", "producto edit success!");
        }catch (Exception e) {
            return message.viewMessage(HttpStatus.NOT_FOUND, "error", "Producto not found!");
        }
    }

    @RequestMapping(value = "api/producto/{id}", method = RequestMethod.DELETE)
    public ResponseEntity deleteProducto(@PathVariable Long id){
        Map<String, String> response = new HashMap<>();
        try {
            Producto producto = productoRepository.findById(id).get();
            productoRepository.delete(producto);
            return message.viewMessage(HttpStatus.OK, "success", "producto delete success!");
        }catch (Exception e){
            return message.viewMessage(HttpStatus.NOT_FOUND, "error", "Producto not found!");
        }
    }
}
