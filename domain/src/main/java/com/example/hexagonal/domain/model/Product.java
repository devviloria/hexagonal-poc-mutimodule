package com.example.hexagonal.domain.model;

import com.example.hexagonal.domain.exception.InvalidProductException;

import java.math.BigDecimal;
import java.util.UUID;

public record Product(UUID id, String name, BigDecimal price) {
    public Product {
        if (id == null) {
            throw new InvalidProductException("El identificador del producto es obligatorio");
        }
        if (name == null || name.isBlank()) {
            throw new InvalidProductException("El nombre del producto es obligatorio");
        }
        if (price == null || price.signum() <= 0) {
            throw new InvalidProductException("El precio debe ser mayor que cero");
        }
        name = name.trim();
    }
}
