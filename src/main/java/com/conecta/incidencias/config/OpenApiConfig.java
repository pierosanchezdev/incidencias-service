package com.conecta.incidencias.config;

import io.swagger.v3.oas.models.*;
import io.swagger.v3.oas.models.info.*;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI apiInfo() {
        return new OpenAPI()
                .info(new Info()
                        .title("API - Gestión de Incidencias Comunales")
                        .description("API REST para la app móvil y la web de gestión de incidencias")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Conecta Call Solutions")
                                .email("soporte@conectacall.com")
                                .url("https://www.conectacall.com")
                        )
                );
    }
}
