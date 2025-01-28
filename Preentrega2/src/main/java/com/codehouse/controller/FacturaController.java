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

import com.codehouse.dto.FacturaDTO;
import com.codehouse.model.Factura;
import com.codehouse.service.FacturaService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import jakarta.persistence.EntityNotFoundException;

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
	
	
	@Operation(
	        summary = "Crear una nueva factura",
	        description = "Crea una factura nueva basada en la información proporcionada.",
	        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = FacturaDTO.class),
	                examples = @ExampleObject(
	                    value = """
	                    {
	                        "cliente": { "id": 1 },
	                        "detalle": [
	                            { "product": { "id": 1 }, "amount": 2 }
	                        ]
	                    }
	                    """
	                )
	            )
	        )
	    )
	    @ApiResponse(
	        responseCode = "201",
	        description = "Factura creada exitosamente",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = Factura.class)
	        )
	    )
	@PostMapping
	public ResponseEntity<Factura> crearFactura(@RequestBody FacturaDTO facturaDTO)
	{
		Factura factura = facturaService.crearFactura(facturaDTO);
	    return ResponseEntity.ok(factura);
	}
	
	
	@Operation(
	        summary = "Modificar una factura existente",
	        description = "Modifica una factura existente con la nueva información proporcionada.",
	        requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
	            content = @Content(
	                mediaType = "application/json",
	                schema = @Schema(implementation = FacturaDTO.class),
	                examples = @ExampleObject(
	                    value = """
	                    {
	                        "cliente": { "id": 3 },
	                        "detalle": [
	                            { "product": { "id": 3 }, "amount": 1 },
	                            { "product": { "id": 2 }, "amount": 1 }
	                        ]
	                    }
	                    """
	                )
	            )
	        )
	    )
	    @ApiResponse(
	        responseCode = "200",
	        description = "Factura modificada exitosamente",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = Factura.class)
	        )
	    )
	@PutMapping("/{id}")
	public ResponseEntity<Factura> modificarFactura(
			@PathVariable Long id, 
			@RequestBody FacturaDTO facturaActualizadaDTO) {
		Factura facturaModificada = facturaService.modificarFactura(id, facturaActualizadaDTO);
		return ResponseEntity.ok(facturaModificada);
	}
	
	
	
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable("id") Long idFactura) {
        try {
            // Llamamos al servicio para eliminar la factura
            facturaService.eliminarFactura(idFactura);
            return ResponseEntity.noContent().build(); // Responde con código 204 No Content si fue exitoso
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // Si no se encuentra la factura
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build(); // Manejo de errores generales
        }
    }
	
	 @ExceptionHandler(ResponseStatusException.class)
	    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
	        // Crear una respuesta personalizada
	        Map<String, String> response = new HashMap<>();
	        response.put("error", ex.getReason());
	        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
	    }


}
