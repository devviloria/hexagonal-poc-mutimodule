package com.example.hexagonal.application.port.in;

import com.example.hexagonal.domain.model.Product;

import java.math.BigDecimal;
import java.util.UUID;

public interface UpdateProductUseCase {
    Product updateProduct(UUID id, String name, BigDecimal price);
}
