package com.tmn.master_service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.tmn.master_service.model.Product;
import com.tmn.master_service.response.ApiResponse;
import com.tmn.master_service.service.ProductService;

@RestController
@RequestMapping("/api/products")
public class ProductController {
	
	@Autowired
    private ProductService productService;

    // @GetMapping
    // public ResponseEntity<List<Product>> getProducts(
    //          @RequestParam(required = false) Long id,
    //          @RequestParam(required = false) String name) {
    //     List<Product> products = productService.getProductsByIdAndName(id, name);
    //     return ResponseEntity.ok(products);
    // }

    @GetMapping
    public ResponseEntity<ApiResponse<List<Product>>> getProducts2(@RequestParam(required = false) Long id,
    @RequestParam(required = false) String name) {
        List<Product> products = productService.getProductsByIdAndName(id, name);
        String message="";
        if(products.size()==0)
            message = "Không có dữ liệu phù hợp";
        ApiResponse<List<Product>> response = new ApiResponse<>(products, message);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }


    @PostMapping
    public Product createProduct(@RequestBody Product product) {
        return productService.save(product);
    }

    @PutMapping("/{id}")
    public Product updateProduct(@PathVariable Long id, @RequestBody Product productDetails) {
        Product product = productService.getById(id)
                .orElseThrow();
        product.setName(productDetails.getName());
        product.setPrice(productDetails.getPrice());

        return productService.save(product);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable Long id) {
        productService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
