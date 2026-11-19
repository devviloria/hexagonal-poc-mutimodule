package com.example.hexagonal.adapter.web.dto;

import com.example.hexagonal.domain.model.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductResponse(UUID id, String name, BigDecimal price) {
    public static ProductResponse from(Product product) {
        return new ProductResponse(product.id(), product.name(), product.price());
    }
}
