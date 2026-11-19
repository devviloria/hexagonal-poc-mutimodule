package com.example.hexagonal.adapter.persistence;

import com.example.hexagonal.application.port.out.ProductRepository;
import com.example.hexagonal.application.service.ProductService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PersistenceConfiguration {
    @Bean
    public ProductService productService(ProductRepository productRepository) {
        return new ProductService(productRepository);
    }
}
