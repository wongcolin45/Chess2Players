package com.chess.api;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class CorsConfig {
  @Bean
  public WebMvcConfigurer corsConfigurer() {
    return new WebMvcConfigurer() {
      @Override
      public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**") // applies to all routes
                .allowedOrigins("*") // allow all origins
                .allowedMethods("*") // allow GET, POST, PUT, DELETE, etc.
                .allowedHeaders("*");
      }
    };
  }
}

