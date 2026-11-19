package com.example.hexagonal.application.exception;

import java.util.UUID;

public class ProductNotFoundException extends RuntimeException {
    public ProductNotFoundException(UUID id) {
        super("No se encontró el producto con identificador: " + id);
    }
}
