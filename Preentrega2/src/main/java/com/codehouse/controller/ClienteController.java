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

import com.codehouse.dto.ClienteDTO;
import com.codehouse.model.Cliente;
import com.codehouse.service.ClienteService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;




@RestController
@RequestMapping("/api/clientes")
public class ClienteController {
	
	@Autowired
    private ClienteService clienteService;
	

	@Operation(
	        summary = "Obtener cliente por ID",
	        description = "Devuelve los detalles de un cliente específico identificado por su ID.",
	        responses = {
	            @ApiResponse(
	                responseCode = "200",
	                description = "Cliente encontrado exitosamente",
	                content = @Content(
	                    mediaType = "application/json",
	                    schema = @Schema(implementation = ClienteDTO.class),
	                    examples = @ExampleObject(
	                        value = """
	                        {
	                            "id": 3,
	                            "nombre": "Martin",
	                            "apellido": "Ferreyra",
	                            "dni": "30099349",
	                            "facturas": [
	                                {
	                                    "id": 2,
	                                    "createdAt": "2025-01-13T03:00:00.000+00:00",
	                                    "total": 100.0,
	                                    "cliente": null,
	                                    "detalle": null
	                                },
	                                {
	                                    "id": 3,
	                                    "createdAt": "2025-01-14T03:00:00.000+00:00",
	                                    "total": 0.0,
	                                    "cliente": null,
	                                    "detalle": null
	                                },
	                                {
	                                    "id": 4,
	                                    "createdAt": "2025-01-14T03:00:00.000+00:00",
	                                    "total": 1408000.0,
	                                    "cliente": null,
	                                    "detalle": null
	                                }
	                            ]
	                        }
	                        """
	                    )
	                )
	            ),
	            @ApiResponse(responseCode = "404", description = "Cliente no encontrado")
	        }
	    )
    @GetMapping("/{id}")
    public ClienteDTO obtenerCliente(@PathVariable @Parameter(description = "ID del cliente") Long id) {
        return clienteService.obtenerClientePorId(id);
    }

	@Operation(
	        summary = "Listar todos los clientes",
	        description = "Devuelve una lista con todos los clientes disponibles."
	    )
	    @ApiResponse(
	        responseCode = "200",
	        description = "Lista de clientes obtenida exitosamente",
	        content = @Content(
	            mediaType = "application/json",
	            schema = @Schema(implementation = ClienteDTO.class)
	        )
	    )
    @GetMapping
    public List<ClienteDTO> listarClientes() {
        return clienteService.listarClientes();
    }
	@Operation(
		    summary = "Crear un nuevo cliente",
		    description = "Crea un cliente nuevo basado en la información proporcionada.",
		    requestBody = @io.swagger.v3.oas.annotations.parameters.RequestBody(
		        content = @Content(
		            mediaType = "application/json",
		            schema = @Schema(implementation = ClienteDTO.class),
		            examples = @ExampleObject(
		                value = """
		                {
		                    "nombre": "Pirulo",
		                    "apellido": "DonNadie",
		                    "dni": "12345678"
		                }
		                """
		            )
		        )
		    )
		)
		@ApiResponse(
		    responseCode = "201",
		    description = "Cliente creado exitosamente",
		    content = @Content(
		        mediaType = "application/json",
		        schema = @Schema(implementation = Cliente.class)
		    )
		)
		@PostMapping
		public Cliente crearCliente(@RequestBody ClienteDTO clienteDTO) {
		    return clienteService.crearCliente(clienteDTO);
		}

    
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarCliente(@PathVariable Long id) {
        clienteService.eliminarCliente(id);
        return ResponseEntity.noContent().build();
    }
    
    @PutMapping("/{id}")
    public ResponseEntity<Cliente> modificarCliente(@PathVariable Long id, @RequestBody Cliente clienteActualizado) {
        var clienteModificado = clienteService.modificarCliente(id, clienteActualizado);
        return ResponseEntity.ok(clienteModificado);
    }
    
    @ExceptionHandler(ResponseStatusException.class)
    public ResponseEntity<Object> handleResponseStatusException(ResponseStatusException ex) {
        // Crear una respuesta personalizada
        Map<String, String> response = new HashMap<>();
        response.put("error", ex.getReason());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }
    

}
