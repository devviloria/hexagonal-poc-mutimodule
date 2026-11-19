package com.example.hexagonal.application.service;

import com.example.hexagonal.application.port.in.CreateProductUseCase;
import com.example.hexagonal.application.port.in.DeleteProductUseCase;
import com.example.hexagonal.application.port.in.GetProductsUseCase;
import com.example.hexagonal.application.port.in.UpdateProductUseCase;
import com.example.hexagonal.application.port.out.ProductRepository;
import com.example.hexagonal.application.exception.ProductNotFoundException;
import com.example.hexagonal.domain.model.Product;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

public class ProductService implements CreateProductUseCase, GetProductsUseCase,
        UpdateProductUseCase, DeleteProductUseCase {
    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public Product createProduct(String name, BigDecimal price) {
        return productRepository.save(new Product(UUID.randomUUID(), name, price));
    }

    @Override
    public List<Product> getProducts() {
        return productRepository.findAll();
    }

    @Override
    public Product updateProduct(UUID id, String name, BigDecimal price) {
        Product existing = productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id));
        return productRepository.save(new Product(existing.id(), name, price));
    }

    @Override
    public void deleteProduct(UUID id) {
        if (!productRepository.deleteById(id)) {
            throw new ProductNotFoundException(id);
        }
    }
}
