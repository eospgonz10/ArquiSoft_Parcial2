package com.udea.ArquiSoft_Parcial2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class OpenApiConfig {

    @Value("${swagger.server.url:http://localhost:8080/api}")
    private String serverUrl;

    @Bean
    public OpenAPI inventarioOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Sistema de Inventario de Almacenes")
                        .description("API RESTful para la gestión de inventarios de productos en almacenes")
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo ArquiSoft Parcial 2")
                                .email("contacto@udea.edu.co")))
                .servers(List.of(
                        new Server()
                                .url(serverUrl)
                                .description("Servidor de desarrollo local")
                ));
    }
}
