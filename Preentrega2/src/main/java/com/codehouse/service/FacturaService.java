package com.codehouse.service;

import java.util.List;

import com.codehouse.dto.FacturaDTO;
import com.codehouse.model.Cliente;
import com.codehouse.model.Factura;

public interface FacturaService {
	
	Factura obtenerFacturaPorId(Long id);
	List<FacturaDTO> listarFactura();
	Factura crearFactura(FacturaDTO facturaDTO);
	void eliminarFactura(Long id);
	Cliente modificarFactura(Long id, Factura facturaActualizado);

}
