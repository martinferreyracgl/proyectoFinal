package com.codehouse.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.codehouse.model.Factura;

public interface FacturaRepository extends JpaRepository<Factura, Long>{

}
