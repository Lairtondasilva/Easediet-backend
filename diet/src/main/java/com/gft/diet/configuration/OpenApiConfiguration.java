package com.gft.diet.configuration;

import org.apache.http.annotation.Contract;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.License;

@OpenAPIDefinition(info = @Info(title = "Diet Service API", version = "V1", description = "Documentation of Diet Service"))
public class OpenApiConfiguration {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components())
                .info(new io.swagger.v3.oas.models.info.Info()
                        .title("Diet Service API")
                        .description("Documentation of Diet Service")
                        .version("V1")
                        .license(
                                new License()
                                        .name("Apache 2.0")
                                        .url("http://springdoc.org")));
    }
}
