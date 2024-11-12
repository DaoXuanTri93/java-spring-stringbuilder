package com.tmn.master_service;


import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import com.tmn.master_service.dto.mapper.UserMapper;
import com.tmn.master_service.dto.mapper.UserCreateMapper;
import com.tmn.master_service.dto.mapper.UserUpdateMapper;

@Configuration
public class WebConfig implements WebMvcConfigurer {
  @Override
  public void addCorsMappings(CorsRegistry registry) {
    registry.addMapping("/api/**")
        .allowedOrigins("http://localhost:8080")
        .allowCredentials(true)
        .allowedMethods("GET", "POST", "PUT", "DELETE", "OPTIONS")
        .allowedHeaders("*");
  }

  @Bean
  public ModelMapper modelMapper() {
    ModelMapper modelMapper = new ModelMapper();
    modelMapper.addMappings(new UserMapper());
    modelMapper.addMappings(new UserCreateMapper());
    modelMapper.addMappings(new UserUpdateMapper());
    return modelMapper;
  }
}