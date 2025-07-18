package com.udea.ArquiSoft_Parcial2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import io.swagger.v3.oas.models.servers.Server;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

/**
 * Configuración de OpenAPI/Swagger para la documentación de la API
 */
@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI inventarioOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("API Sistema de Inventario de Almacenes")
                        .description("""
                                API RESTful para la gestión de inventarios de productos en almacenes.
                                
                                ### Funcionalidades principales:
                                - **Consultar inventario** por almacén específico
                                - **Crear productos** con información completa y stock inicial
                                
                                ### Características técnicas:
                                - Arquitectura en capas (Model, Repository, Service, Controller)
                                - Validación de datos con Bean Validation
                                - Manejo de excepciones globalizado
                                - Versionado de API mediante headers
                                - Respuestas estandarizadas con ApiResponse
                                """)
                        .version("1.0.0")
                        .contact(new Contact()
                                .name("Equipo ArquiSoft Parcial 2")
                                .email("contacto@empresa.com")
                                .url("https://github.com/eospgonz10/ArquiSoft_Parcial2"))
                        .license(new License()
                                .name("MIT License")
                                .url("https://opensource.org/licenses/MIT")))
                .servers(List.of(
                        new Server()
                                .url("http://localhost:8080")
                                .description("Servidor de desarrollo local"),
                        new Server()
                                .url("https://api.inventario.com")
                                .description("Servidor de producción")
                ));
    }
}
