package com.ssafy.voyage.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import java.util.List;

@Configuration
public class CorsConfig {
    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true); // Whether user credentials are supported.

        config.setAllowedHeaders(List.of("Authorization", "Authorization-Refresh", "Content-Type",  "access-control-allow-credentials", "access-control-allow-origin"));
        config.setExposedHeaders(List.of("Authorization", "Authorization-Refresh", "Content-Type",  "access-control-allow-credentials", "access-control-allow-origin"));

        config.setAllowedOrigins(List.of("http://127.0.0.1:9000", "http://localhost:8080", "http://localhost:9000"));
        config.setAllowedMethods(List.of("GET", "POST", "OPTIONS"));

        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", config);

        return source;
    }
}
