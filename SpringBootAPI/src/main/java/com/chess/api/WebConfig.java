package com.chess.api;



import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/**")  // Allow all endpoints
            .allowedOrigins("http://localhost:5173", "https://chess2players.vercel.app")
            .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")  // Allow specific methods
            .allowedHeaders("*")  // Allow all headers
            .allowCredentials(true);  // Allow credentials (if needed)
  }
}
