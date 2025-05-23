package com.example.egypt.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
@EnableWebMvc

public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("https://new-app.lesmysteresdelegypteantique.fr",
                        "https://app.lesmysteresdelegypteantique.fr", "http://localhost:4200")
                .allowedMethods("GET", "POST", "PATCH", "PUT", "DELETE", "HEAD", "OPTIONS")
                .allowedHeaders("Content-Type", "Date", "Total-Count", "loginInfo", "Authorization")
                .exposedHeaders("Content-Type", "Date", "Total-Count", "loginInfo", "Authorization")
                .allowCredentials(true);
    }
}
