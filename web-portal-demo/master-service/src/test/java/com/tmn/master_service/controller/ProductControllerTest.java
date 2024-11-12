package com.tmn.master_service.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.verify;
import java.util.Optional;
import java.util.List;
import java.util.ArrayList;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import com.tmn.master_service.model.Product;
import com.tmn.master_service.response.ApiResponse;
import com.tmn.master_service.service.ProductService;

@ExtendWith(MockitoExtension.class)
public class ProductControllerTest {
    
    @Mock
    private ProductService productService;

     @InjectMocks
    private ProductController controller;

    @BeforeEach
    public void init() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetProducts2ReturnsEmptyList() {                
        when(productService.getProductsByIdAndName(null, null)).thenReturn(new ArrayList<>());
        
        ResponseEntity<ApiResponse<List<Product>>> response = controller.getProducts2(null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().getData().isEmpty());
        assertEquals("Không có dữ liệu phù hợp", response.getBody().getMessage());
    }

    @Test
    void testGetProducts2ReturnsNonEmptyList() {                
        List<Product> mockProducts = List.of(new Product(), new Product());
        when(productService.getProductsByIdAndName(null, null)).thenReturn(mockProducts);
        
        ResponseEntity<ApiResponse<List<Product>>> response = controller.getProducts2(null, null);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().getData().size());
        assertEquals("", response.getBody().getMessage());
    }

    @Test
    void testCreateProduct() {               
        Product product = new Product();
        when(productService.save(any(Product.class))).thenReturn(product);

        Product result = controller.createProduct(product);
        assertEquals(product, result);
    }

    @Test
    void testUpdateProduct() {                
        Product existingProduct = new Product();
        existingProduct.setName("Old Name");
        existingProduct.setPrice(100.0);
        Product updatedProduct = new Product();
        updatedProduct.setName("New Name");
        updatedProduct.setPrice(150.0);
        
        when(productService.getById(any(Long.class))).thenReturn(Optional.of(existingProduct));
        when(productService.save(any(Product.class))).thenReturn(updatedProduct);

        Product result = controller.updateProduct(1L, updatedProduct);
        assertEquals("New Name", result.getName());
        assertEquals(150.0, result.getPrice());
    }

    @Test
    void testDeleteProduct() {                
        doNothing().when(productService).deleteById(any(Long.class));
        
        ResponseEntity<Void> response = controller.deleteProduct(1L);
        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        verify(productService).deleteById(1L);
    }
}
//