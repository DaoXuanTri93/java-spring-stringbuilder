package com.tmn.master_service.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

// import com.tmn.master_service.service.ProductService;
import com.tmn.master_service.model.Product;
import com.tmn.master_service.repository.ProductRepository;

@SpringBootTest
public class ProductServiceTest {

    @Mock
    private ProductRepository productRepository;

    @InjectMocks
    private ProductService productService;

    private Product product1, product2;
    private List<Product> productList;

    @BeforeEach
    public void setup() {
        product1 = new Product(); // Assuming constructor and setter methods set required fields
        product1.setId(1L);
        product1.setName("Product1");

        product2 = new Product();
        product2.setId(2L);
        product2.setName("Product2");

        productList = Arrays.asList(product1, product2);
    }
    
    @Test
    public void testFindAll() {
        when(productRepository.findAll()).thenReturn(productList);
        List<Product> result = productService.findAll();
        assertNotNull(result);
        assertEquals(2, result.size());
        assertEquals(product1, result.get(0));
    }

    @Test
    public void testGetProductsByIdAndName() {
        when(productRepository.findByIdAndName(1L, "Product1")).thenReturn(Arrays.asList(product1));
        List<Product> result = productService.getProductsByIdAndName(1L, "Product1");
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals(product1, result.get(0));

        // Test for no results
        when(productRepository.findByIdAndName(3L, "Product3")).thenReturn(Arrays.asList());
        result = productService.getProductsByIdAndName(3L, "Product3");
        assertTrue(result.isEmpty());
    }

    @Test
    public void testSave() {
        when(productRepository.save(product1)).thenReturn(product1);
        Product result = productService.save(product1);
        assertNotNull(result);
        assertEquals(product1.getId(), result.getId());
    }

    @Test
    public void testDeleteById() {
        doNothing().when(productRepository).deleteById(1L);
        assertDoesNotThrow(() -> productService.deleteById(1L));
        verify(productRepository).deleteById(1L);
    }

    @Test
    public void testGetById() {
        when(productRepository.findById(1L)).thenReturn(Optional.of(product1));
        Optional<Product> result = productService.getById(1L);
        assertTrue(result.isPresent());
        assertEquals(product1, result.get());

        // Test not found scenario
        when(productRepository.findById(3L)).thenReturn(Optional.empty());
        result = productService.getById(3L);
        assertFalse(result.isPresent());
    }
}
//