package com.codehouse.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.codehouse.dto.ProductDTO;
import com.codehouse.entity.Cliente;
import com.codehouse.entity.Product;
import com.codehouse.exception.ClienteNotFoundexception;
import com.codehouse.exception.ProductNotFoundexception;
import com.codehouse.repository.ProductRepository;

@Service
public class ProductServiceImpl implements ProductService {

	@Autowired
	private ProductRepository productRepository;

	@Override
	public ProductDTO obtenerProductPorId(Long id) {
		Product cliente = productRepository.findById(id)
				.orElseThrow(() -> new ProductNotFoundexception("Producto no encontrado con ID: " + id));
		return convertirAProductDTO(cliente);
	}

	@Override
	public List<ProductDTO> listarProducts() {
		return productRepository.findAll().stream().map(this::convertirAProductDTO).collect(Collectors.toList());
	}

	@Override
	public Product crearProduct(ProductDTO productDTO) {
		// Crear entidad producto desde el DTO
		Product producto = new Product();
		producto.setCode(productDTO.getCode());
		producto.setStock(productDTO.getStock());
		producto.setPrice(productDTO.getPrice());
		producto.setDescription(productDTO.getDescription());

       

        // Guardar producto en la base de datos
        return productRepository.save(producto);
	}

	@Override
	public void eliminarProduct(Long id) {
		var product = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundexception("Producto no encontrado con ID: " + id));
		productRepository.delete(product);

	}

	@Override
	public Product modificarProduct(Long id, Product productActualizado) {
		 var productExistente = productRepository.findById(id)
	                .orElseThrow(() -> new ProductNotFoundexception("Producto no encontrado con ID: " + id));

	        // Actualizar los campos necesarios
		 productExistente.setPrice(productActualizado.getPrice());
		 productExistente.setStock(productActualizado.getStock());
		 productExistente.setDescription(productActualizado.getDescription());
		 productExistente.setCode(productActualizado.getCode());
	       

	        return productRepository.save(productExistente);
	}

	private ProductDTO convertirAProductDTO(Product product) {
		ProductDTO dto = new ProductDTO();
		dto.setId(product.getId());
		dto.setPrice(product.getPrice());
		dto.setDescription(product.getDescription());
		dto.setCode(product.getCode());
		dto.setStock(product.getStock());

		return dto;
	}

}
