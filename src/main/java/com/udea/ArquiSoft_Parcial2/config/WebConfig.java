package com.udea.ArquiSoft_Parcial2.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * Configuración básica para la API REST
 * Habilita CORS y configuraciones adicionales
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {
    
    /**
     * Configuración CORS para permitir requests desde diferentes orígenes
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**")
                .allowedOrigins("*")
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
                .allowedHeaders("*")
                .allowCredentials(false);
    }
}
