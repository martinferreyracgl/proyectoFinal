package com.codehouse.service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityNotFoundException;
import jakarta.persistence.PersistenceContext;
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
	
	@PersistenceContext
    private EntityManager entityManager;
	
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
		AtomicReference<Integer> cantidadTotalProductos = new AtomicReference<>(0);
		
		// Obtener el cliente
		Cliente cliente = clienteRepository.findById(facturaDTO.getCliente().getId())
				.orElseThrow(() ->new ResponseStatusException(HttpStatus.NOT_FOUND, "Cliente no encontrado con id: " + facturaDTO.getCliente().getId() + " para facturacion"));

		// Crear Factura
		Factura factura = new Factura();
		factura.setClient(cliente);
		// se obtiene de API externa sino funciona la API externa se devuelve con la clase Date
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
			
			// Acumulamos el total y cantidad de productos
	        total.updateAndGet(val -> val + facturaDetalle.getAmount() * producto.getPrice());
	        cantidadTotalProductos.updateAndGet(val -> val + facturaDetalle.getAmount());

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
		factura.setCantidadProductosVendidos(cantidadTotalProductos.get());
		Factura facturaGuardada = facturaRepository.save(factura);

		// Imprimir advertencias
		if (!advertencias.isEmpty()) {
			System.out.println("Advertencias: " + String.join(", ", advertencias));
		}

		return facturaGuardada;

	}

	

	@Override
	@Transactional
	public Factura modificarFactura(Long idFactura, FacturaDTO facturaDTO) {
	    // 1. Obtener la factura actual
	    Factura factura = facturaRepository.findById(idFactura)
	            .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada"));

	    // 2. Procesar el cliente
	    Cliente cliente = clienteRepository.findById(facturaDTO.getCliente().getId())
	            .orElseThrow(() -> new EntityNotFoundException("Cliente no encontrado"));
	    factura.setClient(cliente);

	    // 3. Actualizar el detalle de la factura
	    Set<FacturaDetalle> detallesAEliminar = new HashSet<>();
	    for (FacturaDetalle detalle : factura.getFacturaDetalles()) {
	        // Verificar si el producto está presente en el detalle recibido y que tenga suficiente stock
	    	Optional<FacturaDetalle> detalleDTO = facturaDTO.getDetalle().stream()
	    	        .filter(d -> d.getProduct().getId().equals(detalle.getProduct().getId()) && 
	    	                    d.getAmount() <= d.getProduct().getStock()) // Verificar stock
	    	        .findFirst();


	        if (detalleDTO.isPresent()) {
	            // Producto está en el detalle recibido, actualizamos cantidad
	        	// la nueva cantidad es menor a la cantidad original repongo stock sino resto
	        	int cantidadOriginal = detalle.getAmount();
	        	int cantidadNueva = detalleDTO.get().getAmount();
	            detalle.setAmount(detalleDTO.get().getAmount());
	            
	            Product producto = detalle.getProduct();
	            detalle.setPrice(producto.getPrice());
	            if (cantidadOriginal<cantidadNueva)
	            {
	            	producto.setStock(producto.getStock() - detalleDTO.get().getAmount());
	            }
	            else if (cantidadOriginal>cantidadNueva) {
	            	producto.setStock(producto.getStock() + detalleDTO.get().getAmount());
				}
	            
	            productRepository.save(producto);
	        } else {
	            // Si no está en el detalle, lo marcamos para eliminación y actualizamos el stock del producto
	            detallesAEliminar.add(detalle);
	            Product producto = detalle.getProduct();
	            producto.setStock(producto.getStock()+detalle.getAmount());
	            productRepository.save(producto);
	        }
	    }

	    // Eliminar productos huérfanos
	    factura.getFacturaDetalles().removeAll(detallesAEliminar);
	    factura.setFacturaDetalles(factura.getFacturaDetalles()); // Esto puede ser necesario para asegurar la actualización de la colección

	    // 4. Insertar nuevos productos (no presentes en la factura)
	    for (FacturaDetalle detalleDTO : facturaDTO.getDetalle()) {
	        if (factura.getFacturaDetalles().stream().noneMatch(d -> d.getProduct().getId().equals(detalleDTO.getProduct().getId()))) {
	            Product producto = productRepository.findById(detalleDTO.getProduct().getId())
	                    .orElseThrow(() -> new EntityNotFoundException("Producto no encontrado"));

	            FacturaDetalle nuevoDetalle = new FacturaDetalle();
	            nuevoDetalle.setFactura(factura);
	            nuevoDetalle.setProduct(producto);
	            nuevoDetalle.setAmount(detalleDTO.getAmount());
	            nuevoDetalle.setPrice(producto.getPrice());
	            producto.setStock(producto.getStock() - detalleDTO.getAmount()); // Reducir el stock
	            productRepository.save(producto);

	            factura.getFacturaDetalles().add(nuevoDetalle);
	        }
	    }
	    
	 // 5. Contabilizar la cantidad total de productos vendidos
	    int cantidadTotalProductosVendidos = factura.getFacturaDetalles().stream()
	            .mapToInt(FacturaDetalle::getAmount)
	            .sum();
	    factura.setCantidadProductosVendidos(cantidadTotalProductosVendidos);
	    
	 // 6. Calcular el total de la factura (precio * cantidad por cada producto)
	    double totalFactura = factura.getFacturaDetalles().stream()
	            .mapToDouble(detalle -> detalle.getPrice() * detalle.getAmount()) // Precio * Cantidad
	            .sum();
	    factura.setTotal(totalFactura); // Establecer el total en la factura

	    // 7. Guardar los cambios de la factura
	    facturaRepository.save(factura);
	    
	    return factura;
	}


	@Transactional
	public void eliminarFactura(Long idFactura) {
	    // 1. Recuperar la factura
	    Factura factura = facturaRepository.findById(idFactura)
	            .orElseThrow(() -> new EntityNotFoundException("Factura no encontrada"));

	    // 2. Actualizar el stock de los productos
	    for (FacturaDetalle detalle : factura.getFacturaDetalles()) {
	        Product producto = detalle.getProduct();
	        producto.setStock(producto.getStock() + detalle.getAmount());
	        productRepository.save(producto);
	    }
	    Cliente cliente = factura.getClient();
	    cliente.getFacturas().remove(factura);
	    
	    factura.getFacturaDetalles().clear();
	    factura.setClient(null);
	    
	    // 3. Eliminar la factura (y sus detalles por cascada)
	    facturaRepository.delete(factura);  // Esto debería eliminar tanto la factura como los detalles

	    
	}
	











	private FacturaDTO convertToFacturaDTO(Factura factura) {
		FacturaDTO dto = new FacturaDTO();
		dto.setId(factura.getId());
		dto.setCreatedAt(factura.getCreatedAt());
		dto.setTotal(factura.getTotal());
		dto.setCliente(factura.getClient());
		dto.setDetalle(factura.getFacturaDetalles());
		dto.setCantidadProductosVendidos(factura.getCantidadProductosVendidos());
		return dto;
	}
}
