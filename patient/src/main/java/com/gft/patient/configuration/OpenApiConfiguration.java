package com.gft.patient.configuration;

import org.springframework.context.annotation.Bean;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition(info = 
@Info(title = "Patient Service API",
    version = "v1",
    description = "Documentation of Patient Service API"))
public class OpenApiConfiguration {
    
    @Bean
    public OpenAPI custmoOpenAPI() {
        return new OpenAPI()
            .components(new Components())
            .info(
                new io.swagger.v3.oas.models.info.Info()
                .title("Patient Service API")
                .version("v1")
                .license(
                    new License()
                        .name("Apache 2.0")
                        .url("http://springdoc.org")
                )
            );
    }
}
