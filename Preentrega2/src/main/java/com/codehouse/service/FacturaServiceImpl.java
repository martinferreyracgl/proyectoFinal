package com.codehouse.service;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.codehouse.apis.service.DateCreateAtServiceImplement;
import com.codehouse.dto.FacturaDTO;
import com.codehouse.model.Cliente;
import com.codehouse.model.Factura;
import com.codehouse.model.FacturaDetalle;
import com.codehouse.model.Product;
import com.codehouse.repository.ClienteRepository;
import com.codehouse.repository.FacturaRepository;
import com.codehouse.repository.ProductRepository;

import jakarta.transaction.Transactional;

@Service
public class FacturaServiceImpl implements FacturaService {

	@Autowired
	private FacturaRepository facturaRepository;
	@Autowired
	private ClienteRepository clienteRepository;
	@Autowired
	private ProductRepository productRepository;
	
	@Autowired
	private DateCreateAtServiceImplement dateService;
	
	@Override
	public FacturaDTO obtenerFacturaPorId(Long id) {
		Factura factura = facturaRepository.findById(id)
				.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Factura no encontrada con ID: " + id));

		return convertToFacturaDTO(factura);
	}

	@Override
	public List<FacturaDTO> listarFactura() {
		return facturaRepository.findAll().stream().map(this::convertToFacturaDTO).collect(Collectors.toList());
	}

	@Override
	@Transactional
	public Factura crearFactura(FacturaDTO facturaDTO) {
		
		// Utilizo AtomicReference por que dentro de las expresiones lamba solo puedo utilizar variables final, de paso investigue un poco
		AtomicReference<Double> total = new AtomicReference<>(0.0);
		
		// Obtener el cliente
		Cliente cliente = clienteRepository.findById(facturaDTO.getCliente().getId())
				.orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con id: " + facturaDTO.getCliente().getId() + " para facturacion"));

		// Crear Factura
		Factura factura = new Factura();
		factura.setClient(cliente);
		// aca se va a cargar una fecha pero desde API externa... coming soon...
		factura.setCreatedAt(dateService.obtenerFechaActual());

		List<String> advertencias = new ArrayList<>();
		
		// Procesar detalle de la factura
		List<FacturaDetalle> lineas = facturaDTO.getDetalle().stream().map(facturaDetalle -> {
			Product producto = productRepository.findById(facturaDetalle.getProduct().getId())
					.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Producto no encontrado para facturacion con id: " + facturaDetalle.getProduct().getId()));

			// validamos stock
			if (producto.getStock() < facturaDetalle.getAmount()) {
				advertencias.add("Stock insuficiente para el producto: " + producto.getDescription());
				return null; // Omitir esta línea
			}

			// Descontamos stock
			producto.setStock(producto.getStock() - facturaDetalle.getAmount());
			productRepository.save(producto);
			
			// Acumulamos el total
		    total.updateAndGet(val -> val + facturaDetalle.getAmount() * producto.getPrice());

			// crear línea detalle factura
			FacturaDetalle linea = new FacturaDetalle();
			linea.setFactura(factura);
			linea.setProduct(producto);
			linea.setAmount(facturaDetalle.getAmount());
			linea.setPrice(producto.getPrice());
			return linea;

		}).filter(linea -> linea != null).collect(Collectors.toList());

		factura.setFacturaDetalles(lineas);
		factura.setTotal(total.get());

		Factura facturaGuardada = facturaRepository.save(factura);

		// Imprimir advertencias
		if (!advertencias.isEmpty()) {
			System.out.println("Advertencias: " + String.join(", ", advertencias));
		}

		return facturaGuardada;

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
