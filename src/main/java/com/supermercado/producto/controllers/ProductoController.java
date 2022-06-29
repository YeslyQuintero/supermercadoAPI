package com.supermercado.producto.controllers;

import com.supermercado.producto.entity.Producto;
import com.supermercado.producto.repository.ProductoRepository;
import com.supermercado.producto.util.JWTUtil;
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
    @Autowired
    private JWTUtil jwtUtil;
    private boolean validarToken(String token){
        String id = jwtUtil.getKey(token);
        return id !=null;
    }

    @RequestMapping(value = "api/producto/{id}", method = RequestMethod.GET)
    public ResponseEntity<Producto> getProducto(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){return null;}
        Optional<Producto> foundProducto = productoRepository.findById(id);
        if(foundProducto.isPresent()){
            return ResponseEntity.ok(foundProducto.get());
        }
        Map<String, String> errorResponse=new HashMap<>();
        return new ResponseEntity(errorResponse,HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "api/producto",method = RequestMethod.POST)
    public ResponseEntity<Optional> createProduct(@RequestBody Producto producto){
        Map<String, String> response = new LinkedHashMap<>();
        try{

            productoRepository.save(producto);
            return message.viewMessage(HttpStatus.OK,"success","registered product success!");
        }catch (Exception e){
            return message.viewMessage(HttpStatus.INTERNAL_SERVER_ERROR,"error","An error occurred while registering the product!");
        }


    }

    @RequestMapping(value = "api/producto", method = RequestMethod.GET)
    public List<Producto> listProducto(@RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){return null;}
        return productoRepository.findAll();

    }

    @RequestMapping(value = "api/producto/{id}", method = RequestMethod.PUT)
    public ResponseEntity editProducto(@RequestBody Producto newProducto, @PathVariable Long id,@RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){return null;}
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
    public ResponseEntity deleteProducto(@PathVariable Long id, @RequestHeader(value = "Authorization") String token){
        if(!validarToken(token)){return null;}
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
