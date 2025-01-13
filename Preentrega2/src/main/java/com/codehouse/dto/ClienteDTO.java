package com.codehouse.dto;

import java.util.List;

import com.codehouse.entity.Factura;

public class ClienteDTO {

    private Long id;
    private String nombre;
    private String apellido;
    private String dni;
   private List<FacturaDTO> facturas;

    // Getters y setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

	public String getDni() {
		return dni;
	}

	public void setDni(String dni) {
		this.dni = dni;
	}

	public List<FacturaDTO> getFacturas() {
		return facturas;
	}

	public void setFacturas(List<FacturaDTO> facturas) {
		this.facturas = facturas;
	}

    
}

