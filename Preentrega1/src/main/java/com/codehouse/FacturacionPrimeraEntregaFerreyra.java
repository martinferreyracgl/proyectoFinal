package com.codehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.codehouse.dao.DaoFactory;
import com.codehouse.models.Cliente;

@SpringBootApplication
public class FacturacionPrimeraEntregaFerreyra implements CommandLineRunner {

  @Autowired
  private DaoFactory dao;

  public static void main(String[] args) {

    SpringApplication.run(FacturacionPrimeraEntregaFerreyra.class, args);
  }

  @Override
  public void run(String... args) throws Exception {

    try {
      Cliente cliente1 = new Cliente();
      cliente1.setDni(28655323);
      cliente1.setNombre("tincho");
      cliente1.setApellido("quirquincho");
      this.dao.persistirCliente(cliente1);
    } catch (Exception e) {
      e.printStackTrace(System.err);
    }

  }

}
