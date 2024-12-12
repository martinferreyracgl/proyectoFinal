package com.codehouse.models;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @author bizit
 *
 */
@Entity
@Table(name = "Clientes")
public class Cliente {

  @Id // Primary Key
  @GeneratedValue(strategy = GenerationType.IDENTITY) // Autoincremental
  private Long id;

  @Column(unique = true, nullable = false)
  private int dni;

  /**
   * @return dni
   */
  public int getDni() {

    return this.dni;
  }

  /**
   * @param dni new value of {@link #getdni}.
   */
  public void setDni(int dni) {

    this.dni = dni;
  }

  private String nombre;

  /**
   * @return id
   */
  public Long getId() {

    return this.id;
  }

  /**
   * @param id new value of {@link #getid}.
   */
  public void setId(Long id) {

    this.id = id;
  }

  private String apellido;

  /**
   * @return nombre
   */
  public String getNombre() {

    return this.nombre;
  }

  /**
   * @return apellido
   */
  public String getApellido() {

    return this.apellido;
  }

  /**
   * @param nombre new value of {@link #getnombre}.
   */
  public void setNombre(String nombre) {

    this.nombre = nombre;
  }

  /**
   * @param apellido new value of {@link #getapellido}.
   */
  public void setApellido(String apellido) {

    this.apellido = apellido;
  }

}
