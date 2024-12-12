package com.codehouse.models;

import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;

@Entity // Define que esta clase es una entidad JPA
@Table(name = "clientes") // Asocia esta entidad con la tabla 'clients'
public class Cliente { // Clase que representa la tabla 'clients'

  @Id // Define el campo 'id' como clave primaria
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Indica que el valor del campo será generado automáticamente por
                                                      // la base de datos
  private int id; // Campo que almacena el identificador único del cliente

  @Column(name = "name", length = 75, nullable = false) // Mapea el campo 'name' con la columna 'name' de la tabla
  private String name; // Campo que almacena el nombre del cliente

  @Column(name = "lastname", length = 75, nullable = false) // Mapea el campo 'lastname' con la columna 'lastname' de la
                                                            // tabla
  private String lastname; // Campo que almacena el apellido del cliente

  @Column(name = "docnumber", length = 11, nullable = false, unique = true) // Mapea el campo 'docnumber' con la columna
                                                                            // 'docnumber', que debe ser único
  private String docnumber; // Campo que almacena el número de documento del cliente

  // Define la relación uno a muchos con la clase Factura
  @OneToMany(mappedBy = "client", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY) // Define la

  private List<Factura> facturas; // Lista de facturas asociadas al cliente

  /**
   * @return id
   */
  public int getId() {

    return this.id;
  }

  /**
   * @return name
   */
  public String getName() {

    return this.name;
  }

  /**
   * @return lastname
   */
  public String getLastname() {

    return this.lastname;
  }

  /**
   * @return docnumber
   */
  public String getDocnumber() {

    return this.docnumber;
  }

  /**
   * @return facturas
   */
  public List<Factura> getFacturas() {

    return this.facturas;
  }

  /**
   * @param id new value of {@link #getid}.
   */
  public void setId(int id) {

    this.id = id;
  }

  /**
   * @param name new value of {@link #getname}.
   */
  public void setName(String name) {

    this.name = name;
  }

  /**
   * @param lastname new value of {@link #getlastname}.
   */
  public void setLastname(String lastname) {

    this.lastname = lastname;
  }

  /**
   * @param docnumber new value of {@link #getdocnumber}.
   */
  public void setDocnumber(String docnumber) {

    this.docnumber = docnumber;
  }

  /**
   * @param facturas new value of {@link #getfacturas}.
   */
  public void setFacturas(List<Factura> facturas) {

    this.facturas = facturas;
  }

}
