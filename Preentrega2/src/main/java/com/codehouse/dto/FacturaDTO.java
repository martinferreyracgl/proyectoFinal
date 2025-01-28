package com.codehouse.dto;

import java.util.Date;
import java.util.List;

import com.codehouse.model.Cliente;
import com.codehouse.model.FacturaDetalle;

public class FacturaDTO {
	
	 private Long id;
	    private Date createdAt;
	    private double total;
	    private double cantidadProductosVendidos;
	    private Cliente cliente;
	    private List<FacturaDetalle> detalle;
		public Long getId() {
			return id;
		}
		public void setId(Long id) {
			this.id = id;
		}
		public Date getCreatedAt() {
			return createdAt;
		}
		public void setCreatedAt(Date createdAt) {
			this.createdAt = createdAt;
		}
		public double getTotal() {
			return total;
		}
		public void setTotal(double total) {
			this.total = total;
		}
		public Cliente getCliente() {
			return cliente;
		}
		public void setCliente(Cliente cliente) {
			this.cliente = cliente;
		}
		public List<FacturaDetalle> getDetalle() {
			return detalle;
		}
		public void setDetalle(List<FacturaDetalle> detalle) {
			this.detalle = detalle;
		}
		public double getCantidadProductosVendidos() {
			return cantidadProductosVendidos;
		}
		public void setCantidadProductosVendidos(double cantidadProductosVendidos) {
			this.cantidadProductosVendidos = cantidadProductosVendidos;
		}

}
