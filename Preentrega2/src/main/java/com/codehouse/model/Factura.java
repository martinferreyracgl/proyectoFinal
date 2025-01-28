package com.codehouse.model;

import java.util.Date; // Importa la clase Date para trabajar con fechas
import java.util.List; // Importa la clase List para manejar colecciones

import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

/**
 * @author bizit
 *
 */
// Importa las anotaciones necesarias para trabajar con JPA
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;

@Entity // Define que esta clase es una entidad JPA
@Table(name = "factura") // Asocia esta entidad con la tabla 'Factura'
public class Factura { // Clase que representa la tabla 'Factura'

  @Id // Define el campo 'id' como clave primaria
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor del campo será generado automáticamente por
                                                      // la base de datos
  private Long id; // Campo que almacena el identificador único de la factura

  @ManyToOne // Define la relación muchos a uno con la clase Client
  @JoinColumn(name = "client_id", nullable = false) // Especifica la columna de la clave foránea en la tabla 'factura'
  private Cliente client; // Campo que almacena el cliente asociado a la factura

  @Column(name = "created_at", nullable = false) // Mapea el campo 'createdAt' con la columna 'created_at' de la tabla
  @Temporal(TemporalType.TIMESTAMP) // Indica que el campo es de tipo fecha y hora
  private Date createdAt; // Campo que almacena la fecha de creación de la factura

  @Column(name = "total", nullable = false) // Mapea el campo 'total' con la columna 'total' de la tabla
  private double total; // Campo que almacena el total de la factura
  
  @Column(name = "cantidadProductosVendidos", nullable = false) // Mapea el campo 'cantidadProductosVendidos' con la columna 'cantidadProductosVendidos' de la tabla
  private double cantidadProductosVendidos; // Campo que almacena el total de productos vendidos

  @OneToMany(mappedBy = "factura", cascade = CascadeType.ALL, orphanRemoval = true) // Define la relación uno a muchos                                                                               // con la clase FacturaDetalle
  private List<FacturaDetalle> facturaDetalles; // Lista de detalles asociados a la factura

  // Getters and Setters
  /**
   * @return id
   */
  public Long getId() {

    return this.id;
  }

  /**
   * @return client
   */
  public Cliente getClient() {

    return this.client;
  }

  /**
   * @return createdAt
   */
  public Date getCreatedAt() {

    return this.createdAt;
  }

  /**
   * @return total
   */
  public double getTotal() {

    return this.total;
  }

  /**
   * @return facturaDetalles
   */
  public List<FacturaDetalle> getFacturaDetalles() {

    return this.facturaDetalles;
  }

  /**
   * @param id new value of {@link #getid}.
   */
  public void setId(Long id) {

    this.id = id;
  }

  /**
   * @param client new value of {@link #getclient}.
   */
  public void setClient(Cliente client) {

    this.client = client;
  }

  /**
   * @param createdAt new value of {@link #getcreatedAt}.
   */
  public void setCreatedAt(Date createdAt) {

    this.createdAt = createdAt;
  }

  /**
   * @param total new value of {@link #gettotal}.
   */
  public void setTotal(double total) {

    this.total = total;
  }

  /**
   * @param facturaDetalles new value of {@link #getfacturaDetalles}.
   */
  public void setFacturaDetalles(List<FacturaDetalle> facturaDetalles) {

    this.facturaDetalles = facturaDetalles;
  }

public double getCantidadProductosVendidos() {
	return cantidadProductosVendidos;
}

public void setCantidadProductosVendidos(double cantidadProductosVendidos) {
	this.cantidadProductosVendidos = cantidadProductosVendidos;
}

}
