package com.codehouse.entity;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

/**
 * @author bizit
 *
 */
@Entity // Define que esta clase es una entidad JPA
@Table(name = "products") // Asocia esta entidad con la tabla 'products'
public class Product { // Clase que representa la tabla 'products'

  @Id // Define el campo 'id' como clave primaria
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor del campo será generado automáticamente por
                                                      // la base de datos
  private Long id; // Campo que almacena el identificador único del producto

  @Column(name = "description", length = 150, nullable = false) // Mapea el campo 'description' con la columna
                                                                // 'description' de la tabla
  private String description; // Campo que almacena la descripción del producto

  @Column(name = "code", length = 50, nullable = false, unique = true) // Mapea el campo 'code' con la columna 'code',
                                                                       // que debe ser único
  private String code; // Campo que almacena el código único del producto

  @Column(name = "stock", nullable = false) // Mapea el campo 'stock' con la columna 'stock' de la tabla
  private int stock; // Campo que almacena la cantidad disponible del producto

  @Column(name = "price", nullable = false) // Mapea el campo 'price' con la columna 'price' de la tabla
  private double price; // Campo que almacena el precio del producto

  @OneToMany(mappedBy = "product", cascade = CascadeType.ALL, orphanRemoval = true) // Define la relación uno a muchos
                                                                                    // con la clase FacturaDetalle
  private List<FacturaDetalle> facturaDetalles; // Lista de detalles asociados al producto

  // Getters y Setters
  /**
   * @return id
   */
  public Long getId() {

    return this.id;
  }

  /**
   * @return description
   */
  public String getDescription() {

    return this.description;
  }

  /**
   * @return code
   */
  public String getCode() {

    return this.code;
  }

  /**
   * @return stock
   */
  public int getStock() {

    return this.stock;
  }

  /**
   * @return price
   */
  public double getPrice() {

    return this.price;
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
   * @param description new value of {@link #getdescription}.
   */
  public void setDescription(String description) {

    this.description = description;
  }

  /**
   * @param code new value of {@link #getcode}.
   */
  public void setCode(String code) {

    this.code = code;
  }

  /**
   * @param stock new value of {@link #getstock}.
   */
  public void setStock(int stock) {

    this.stock = stock;
  }

  /**
   * @param price new value of {@link #getprice}.
   */
  public void setPrice(double price) {

    this.price = price;
  }

  /**
   * @param facturaDetalles new value of {@link #getfacturaDetalles}.
   */
  public void setFacturaDetalles(List<FacturaDetalle> facturaDetalles) {

    this.facturaDetalles = facturaDetalles;
  }

}