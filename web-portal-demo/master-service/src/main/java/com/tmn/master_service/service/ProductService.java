package com.tmn.master_service.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.tmn.master_service.model.Product;
import com.tmn.master_service.repository.ProductRepository;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    public List<Product> findAll() {
        return productRepository.findAll();
    }

    public List<Product> getProductsByIdAndName(Long id, String name) {
        return productRepository.findByIdAndName(id, name);
    }

    public Product save(Product product) {
        return productRepository.save(product);
    }

    public void deleteById(Long id) {
        productRepository.deleteById(id);
    }
    
    public Optional<Product> getById(Long id) {
    	return productRepository.findById(id);
    }
}
