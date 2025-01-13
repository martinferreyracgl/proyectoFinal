package com.codehouse.service;

import java.util.List;

import com.codehouse.dto.ProductDTO;
import com.codehouse.entity.Product;

public interface ProductService {
	
	ProductDTO obtenerProductPorId(Long id);
	List<ProductDTO> listarProducts();
	Product crearProduct(ProductDTO productDTO);
	void eliminarProduct(Long id);
	Product modificarProduct(Long id, Product clienteActualizado);

}
