package com.codehouse.service;

import java.util.List;

import com.codehouse.dto.FacturaDTO;
import com.codehouse.model.Cliente;
import com.codehouse.model.Factura;

public interface FacturaService {
	
	FacturaDTO obtenerFacturaPorId(Long id);
	List<FacturaDTO> listarFactura();
	Factura crearFactura(FacturaDTO facturaDTO);
	void eliminarFactura(Long id);
	Factura modificarFactura(Long id, FacturaDTO facturaActualizadaDTO);

}
