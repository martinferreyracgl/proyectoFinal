package com.codehouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.codehouse.dto.ProductDTO;
import com.codehouse.model.Cliente;
import com.codehouse.model.Product;
import com.codehouse.service.ProductService;

@RestController
@RequestMapping("/api/productos")
public class ProductController {
	
	@Autowired
    private ProductService productService;
	
	@GetMapping
    public List<ProductDTO> listarProductos() {
        return productService.listarProducts();
    }
	
	@GetMapping("/{id}")
    public ProductDTO obtenerCliente(@PathVariable Long id) {
        return productService.obtenerProductPorId(id);
    }
	
	
	@PostMapping
    public Product crearProduct(@RequestBody ProductDTO productDTO) {
        return productService.crearProduct(productDTO);
    }
	
	 @DeleteMapping("/{id}")
	    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
		 productService.eliminarProduct(id);
	        return ResponseEntity.noContent().build();
	    }
	 
	 @PutMapping("/{id}")
	    public ResponseEntity<Product> modificarCliente(@PathVariable Long id, @RequestBody Product productActualizado) {
	        var productModificado = productService.modificarProduct(id, productActualizado);
	        return ResponseEntity.ok(productModificado);
	    }
	 
	 @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
	        // Crear una respuesta personalizada
	        Map<String, String> response = new HashMap<>();
	        response.put("error", ex.getReason());
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }
	    

}
