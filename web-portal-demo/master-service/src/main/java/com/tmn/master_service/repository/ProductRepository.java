package com.tmn.master_service.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.tmn.master_service.model.Product;
import java.util.List;


public interface ProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT p FROM Product p WHERE (:id IS NULL OR p.id = :id) AND (:name IS NULL OR p.name LIKE %:name%)")
    List<Product> findByIdAndName(@Param("id") Long id, @Param("name") String name);

}
