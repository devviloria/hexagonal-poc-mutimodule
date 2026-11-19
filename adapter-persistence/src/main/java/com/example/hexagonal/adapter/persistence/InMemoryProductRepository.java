package com.example.hexagonal.adapter.persistence;

import com.example.hexagonal.application.port.out.ProductRepository;
import com.example.hexagonal.domain.model.Product;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

@Repository
public class InMemoryProductRepository implements ProductRepository {
    private final ConcurrentMap<UUID, Product> products = new ConcurrentHashMap<>();

    @Override
    public Product save(Product product) {
        products.put(product.id(), product);
        return product;
    }

    @Override
    public List<Product> findAll() {
        return List.copyOf(products.values());
    }

    @Override
    public Optional<Product> findById(UUID id) {
        return Optional.ofNullable(products.get(id));
    }

    @Override
    public boolean deleteById(UUID id) {
        return products.remove(id) != null;
    }
}
