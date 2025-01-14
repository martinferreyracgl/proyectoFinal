package com.codehouse.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

/**
 * @author bizit
 *
 */
@Entity // Define que esta clase es una entidad JPA
@Table(name = "factura_detalle") // Asocia esta entidad con la tabla 'factura_detalle'
public class FacturaDetalle { // Clase que representa la tabla 'invoice_details'

  @Id // Define el campo 'invoiceDetailId' como clave primaria
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor del campo será generado automáticamente por
                                                      // la base de datos
  private int facturaDetalleId; // Campo que almacena el identificador único del detalle de la factura

  @ManyToOne // Define la relación muchos a uno con la clase Factura
  @JoinColumn(name = "factura_id", nullable = false) // Especifica la columna de la clave foránea en la tabla
  @JsonIgnore                                                   // 'factura_detalle'
  private Factura factura; // Campo que almacena la factura asociada al detalle

  @ManyToOne // Define la relación muchos a uno con la clase Product
  @JoinColumn(name = "product_id", nullable = false) // Especifica la columna de la clave foránea en la tabla
                                                     // 'factura_detalle'
  private Product product; // Campo que almacena el producto asociado al detalle

  @Column(name = "amount", nullable = false) // Mapea el campo 'amount' con la columna 'amount' de la tabla
  private int amount; // Campo que almacena la cantidad del producto en el detalle

  @Column(name = "price", nullable = false) // Mapea el campo 'price' con la columna 'price' de la tabla
  private double price; // Campo que almacena el precio del producto en el detalle

  // Getters y Setters

  /**
   * @return factura
   */
  public Factura getFactura() {

    return this.factura;
  }

  /**
   * @return product
   */
  public Product getProduct() {

    return this.product;
  }

  /**
   * @return amount
   */
  public int getAmount() {

    return this.amount;
  }

  /**
   * @return price
   */
  public double getPrice() {

    return this.price;
  }

  /**
   * @param factura new value of {@link #getfactura}.
   */
  public void setFactura(Factura factura) {

    this.factura = factura;
  }

  /**
   * @param product new value of {@link #getproduct}.
   */
  public void setProduct(Product product) {

    this.product = product;
  }

  /**
   * @param amount new value of {@link #getamount}.
   */
  public void setAmount(int amount) {

    this.amount = amount;
  }

  /**
   * @param price new value of {@link #getprice}.
   */
  public void setPrice(double price) {

    this.price = price;
  }

  /**
   * @return facturaDetalleId
   */
  public int getFacturaDetalleId() {

    return this.facturaDetalleId;
  }

  /**
   * @param facturaDetalleId new value of {@link #getfacturaDetalleId}.
   */
  public void setFacturaDetalleId(int facturaDetalleId) {

    this.facturaDetalleId = facturaDetalleId;
  }

}