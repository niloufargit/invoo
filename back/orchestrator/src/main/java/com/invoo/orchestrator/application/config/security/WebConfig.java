package com.invoo.orchestrator.application.config.security;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // This allows all paths in your application
                .allowedOrigins("http://localhost:4200") // Allow frontend origin, adjust port as necessary
                .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS") // Allowed HTTP methods
                .allowedHeaders("*") // Allows all headers
                .allowCredentials(true) // If you need cookies or auth headers
                .maxAge(3600); // Cache duration for CORS preflight requests
    }
}
