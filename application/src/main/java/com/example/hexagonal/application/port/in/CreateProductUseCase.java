package com.example.hexagonal.application.port.in;

import com.example.hexagonal.domain.model.Product;

import java.math.BigDecimal;

public interface CreateProductUseCase {
    Product createProduct(String name, BigDecimal price);
}
