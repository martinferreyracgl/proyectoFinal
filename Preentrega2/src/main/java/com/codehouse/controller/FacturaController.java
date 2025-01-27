package com.codehouse.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.codehouse.dto.FacturaDTO;
import com.codehouse.model.Factura;
import com.codehouse.service.FacturaService;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {
	
	@Autowired
    private FacturaService facturaService;
	
	@GetMapping
    public List<FacturaDTO> listarFacturas() {
        return facturaService.listarFactura();
    }
	
	@GetMapping("/{id}")
    public FacturaDTO obtenerFacturaById(@PathVariable Long id) {
        return facturaService.obtenerFacturaPorId(id);
    }
	
	@PostMapping
	public ResponseEntity<Factura> crearFactura(@RequestBody FacturaDTO facturaDTO)
	{
		Factura factura = facturaService.crearFactura(facturaDTO);
	    return ResponseEntity.ok(factura);
	}
	
	 @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
	        // Crear una respuesta personalizada
	        Map<String, String> response = new HashMap<>();
	        response.put("error", ex.getReason());
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }


}
