package com.codehouse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

import com.codehouse.model.Cliente;

@SpringBootApplication
public class FacturacionSegundaEntregaFerreyra  {

  

  public static void main(String[] args) {

    SpringApplication.run(FacturacionSegundaEntregaFerreyra.class, args);
  }
  
  @Bean
	RestTemplate restTemplate() {
		return new RestTemplate();
	}

 

}
