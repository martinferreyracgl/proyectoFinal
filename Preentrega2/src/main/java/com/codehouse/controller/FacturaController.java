package com.codehouse.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.codehouse.dto.FacturaDTO;
import com.codehouse.service.FacturaService;

@RestController
@RequestMapping("/api/facturas")
public class FacturaController {
	
	@Autowired
    private FacturaService facturaService;
	
	@GetMapping
    public List<FacturaDTO> listarFacturas() {
        return facturaService.listarFactura();
    }

}
