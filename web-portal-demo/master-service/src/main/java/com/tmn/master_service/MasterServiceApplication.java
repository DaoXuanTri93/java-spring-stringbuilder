package com.tmn.master_service;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EntityScan(basePackages = "com.tmn.master_service.model") // Chỉ định package chứa các entity
@EnableJpaRepositories(basePackages = "com.tmn.master_service.repository") // Chỉ định package chứa các repository
public class MasterServiceApplication {
	public static void main(String[] args) {
		SpringApplication.run(MasterServiceApplication.class, args);
	}
}
