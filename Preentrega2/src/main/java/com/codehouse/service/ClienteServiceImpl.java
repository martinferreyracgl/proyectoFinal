package com.codehouse.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codehouse.dto.ClienteDTO;
import com.codehouse.dto.FacturaDTO;
import com.codehouse.model.Cliente;
import com.codehouse.model.Factura;
import com.codehouse.repository.ClienteRepository;

@Service
public class ClienteServiceImpl implements ClienteService {
	
	@Autowired
    private ClienteRepository clienteRepository;

	@Override
	public ClienteDTO obtenerClientePorId(Long id) {
		Cliente cliente = clienteRepository.findById(id)
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con ID: " + id));
		return convertirAClienteDTO(cliente);
	}
	@Override
	public List<ClienteDTO> listarClientes() {
		return clienteRepository.findAll().stream()
                .map(this::convertirAClienteDTO)
                .collect(Collectors.toList());
	}
	
	 private ClienteDTO convertirAClienteDTO(Cliente cliente) {
	        ClienteDTO dto = new ClienteDTO();
	        dto.setId(cliente.getId());
	        dto.setNombre(cliente.getName());
	        dto.setApellido(cliente.getLastname());
	        dto.setDni(cliente.getDocnumber());
	        dto.setFacturas(cliente.getFacturas().stream().map(this::convertToFacturaDTO).toList());
	       
	        return dto;
	    }
	 
	 private FacturaDTO convertToFacturaDTO(Factura factura) {
		    FacturaDTO dto = new FacturaDTO();
		    dto.setId(factura.getId());
		    dto.setCreatedAt(factura.getCreatedAt());
		    dto.setTotal(factura.getTotal());
		    dto.setCantidadProductosVendidos(factura.getCantidadProductosVendidos());
		    return dto;
		}
	@Override
	public Cliente crearCliente(ClienteDTO clienteDTO) {
		// Crear entidad Cliente desde el DTO
        Cliente cliente = new Cliente();
        cliente.setName(clienteDTO.getNombre());
        cliente.setLastname(clienteDTO.getApellido());
        
        cliente.setDocnumber(clienteDTO.getDni());

        // Manejar facturas opcionales
       /* Optional.ofNullable(clienteDTO.getFacturas()).ifPresent(facturas -> {
            // Guardar las facturas y asociarlas al cliente
            for (Factura factura : facturas) {
                factura.setCliente(cliente); // Asegúrate de que Factura tenga referencia al cliente
                facturaRepository.save(factura);
            }
            cliente.setFacturas(facturas);
        });*/

        // Guardar cliente en la base de datos
        return clienteRepository.save(cliente);
	}
	@Override
	public void eliminarCliente(Long id) {
		var cliente = clienteRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con ID: " + id));
		clienteRepository.delete(cliente);
	}
	@Override
	public Cliente modificarCliente(Long id, Cliente clienteActualizado) {
		 var clienteExistente = clienteRepository.findById(id)
	                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con ID: " + id));

	        // Actualizar los campos necesarios
	        clienteExistente.setName(clienteActualizado.getName());
	        clienteExistente.setLastname(clienteActualizado.getLastname());

	        return clienteRepository.save(clienteExistente);
	}

}
