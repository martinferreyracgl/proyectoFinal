package com.codehouse;


import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.responses.ApiResponse;

@Configuration

public class SwaggerConfig {
    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info().title("Documentacion BackEnd Sistema Facturación")
                .description("Este sistema de facturación permite gestionar clientes, productos, y la gestión de facturas con sus respectivos detalles.")
                .version("1.0.0"));
    }
    
   
    
 
}