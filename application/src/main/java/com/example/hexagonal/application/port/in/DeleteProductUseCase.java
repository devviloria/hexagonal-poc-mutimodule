package com.example.hexagonal.application.port.in;

import java.util.UUID;

public interface DeleteProductUseCase {
    void deleteProduct(UUID id);
}
