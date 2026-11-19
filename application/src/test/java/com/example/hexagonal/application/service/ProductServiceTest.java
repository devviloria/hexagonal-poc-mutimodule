package com.example.hexagonal.application.service;

import com.example.hexagonal.application.exception.ProductNotFoundException;
import com.example.hexagonal.application.port.out.ProductRepository;
import com.example.hexagonal.domain.model.Product;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class ProductServiceTest {
    @Test
    void updatesExistingProduct() {
        UUID id = UUID.randomUUID();
        InMemoryProductRepositoryStub repository = new InMemoryProductRepositoryStub();
        repository.save(new Product(id, "Teclado", new BigDecimal("25.50")));
        ProductService service = new ProductService(repository);

        Product updated = service.updateProduct(id, "Monitor", new BigDecimal("199.99"));

        assertEquals(id, updated.id());
        assertEquals("Monitor", updated.name());
        assertEquals(new BigDecimal("199.99"), updated.price());
    }

    @Test
    void throwsWhenUpdatingMissingProduct() {
        ProductService service = new ProductService(new InMemoryProductRepositoryStub());

        assertThrows(
                ProductNotFoundException.class,
                () -> service.updateProduct(UUID.randomUUID(), "Monitor", new BigDecimal("199.99"))
        );
    }

    @Test
    void deletesExistingProductAndThrowsWhenItIsMissing() {
        UUID id = UUID.randomUUID();
        InMemoryProductRepositoryStub repository = new InMemoryProductRepositoryStub();
        repository.save(new Product(id, "Teclado", new BigDecimal("25.50")));
        ProductService service = new ProductService(repository);

        service.deleteProduct(id);

        assertEquals(List.of(), service.getProducts());
        assertThrows(ProductNotFoundException.class, () -> service.deleteProduct(id));
    }

    private static class InMemoryProductRepositoryStub implements ProductRepository {
        private final Map<UUID, Product> products = new ConcurrentHashMap<>();

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
}
