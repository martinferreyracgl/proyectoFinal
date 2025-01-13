package com.codehouse.service;

import java.util.List;

import com.codehouse.dto.ClienteDTO;
import com.codehouse.entity.Cliente;

public interface ClienteService {
	
	ClienteDTO obtenerClientePorId(Long id);
	List<ClienteDTO> listarClientes();
	Cliente crearCliente(ClienteDTO clienteDTO);
	void eliminarCliente(Long id);
	Cliente modificarCliente(Long id, Cliente clienteActualizado);
	

}
