package com.codehouse.dao;

import org.springframework.stereotype.Service;

import com.codehouse.models.Cliente;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;

/**
 * @author bizit
 *
 */
@Service
public class DaoFactory {

  @PersistenceContext
  private EntityManager em;

  @Transactional
  public void persistirCliente(Cliente cliente) {

    this.em.persist(cliente);
  }

}
