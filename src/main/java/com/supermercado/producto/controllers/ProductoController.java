package com.supermercado.producto.controllers;

package com.supermercado.producto.entity;
package com.supermercado.producto.repository;
import com.supermercado.producto.entity.Producto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController

public class ProductoController<ProductoRepository> {
    @Autowired
    private ProductoRepository productoRepository;

    @RequestMapping(value = "api/users/{id}", method = RequestMethod.GET)
    public ResponseEntity<Producto> getUser(@PathVariable Long id){
        Optional<Producto> foundUser = productoRepository.findById(id);
        if(foundProducto.isPresent()){
            return ResponseEntity.ok(foundUser.get());
        }
        Map<String,String> errorResponse = new LinkedHashMap<>();
        errorResponse.put("error","Not found");
        errorResponse.put("message","Producto not found");
        errorResponse.put("status", HttpStatus.NOT_FOUND.toString());
        return new ResponseEntity(errorResponse, HttpStatus.NOT_FOUND);
    }

    @RequestMapping(value = "api/users", method = RequestMethod.POST)
    public  Producto createUser(@RequestBody Producto producto){
        return productoRepository.save(producto);
    }

    @RequestMapping(value = "api/users", method = RequestMethod.GET)
    public List<Producto> listUser(){
        return productoRepository.findAll();

    }
}
