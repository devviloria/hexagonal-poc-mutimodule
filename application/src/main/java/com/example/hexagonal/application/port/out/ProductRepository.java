package com.example.hexagonal.application.port.out;

import com.example.hexagonal.domain.model.Product;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ProductRepository {
    Product save(Product product);
    List<Product> findAll();
    Optional<Product> findById(UUID id);
    boolean deleteById(UUID id);
}
