package com.codehouse.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codehouse.dto.FacturaDTO;
import com.codehouse.model.Cliente;
import com.codehouse.model.Factura;
import com.codehouse.repository.FacturaRepository;

@Service
public class FacturaServiceImpl implements FacturaService {
	
	@Autowired
    private FacturaRepository facturaRepository;

	@Override
	public Factura obtenerFacturaPorId(Long id) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<FacturaDTO> listarFactura() {
		return facturaRepository.findAll().stream()
                .map(this::convertToFacturaDTO)
                .collect(Collectors.toList());
	}

	@Override
	public Factura crearFactura(FacturaDTO facturaDTO) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void eliminarFactura(Long id) {
		// TODO Auto-generated method stub

	}

	@Override
	public Cliente modificarFactura(Long id, Factura facturaActualizado) {
		// TODO Auto-generated method stub
		return null;
	}
	
	 private FacturaDTO convertToFacturaDTO(Factura factura) {
		    FacturaDTO dto = new FacturaDTO();
		    dto.setId(factura.getId());
		    dto.setCreatedAt(factura.getCreatedAt());
		    dto.setTotal(factura.getTotal());
		    dto.setCliente(factura.getClient());
		    dto.setDetalle(factura.getFacturaDetalles());
		    return dto;
		}
}
