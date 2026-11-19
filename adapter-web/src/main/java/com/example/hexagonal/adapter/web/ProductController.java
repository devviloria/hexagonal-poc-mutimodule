package com.example.hexagonal.adapter.web;

import com.example.hexagonal.adapter.web.dto.CreateProductRequest;
import com.example.hexagonal.adapter.web.dto.ProductResponse;
import com.example.hexagonal.adapter.web.dto.UpdateProductRequest;
import com.example.hexagonal.application.port.in.CreateProductUseCase;
import com.example.hexagonal.application.port.in.DeleteProductUseCase;
import com.example.hexagonal.application.port.in.GetProductsUseCase;
import com.example.hexagonal.application.port.in.UpdateProductUseCase;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final CreateProductUseCase createProductUseCase;
    private final GetProductsUseCase getProductsUseCase;
    private final UpdateProductUseCase updateProductUseCase;
    private final DeleteProductUseCase deleteProductUseCase;

    public ProductController(CreateProductUseCase createProductUseCase,
                             GetProductsUseCase getProductsUseCase,
                             UpdateProductUseCase updateProductUseCase,
                             DeleteProductUseCase deleteProductUseCase) {
        this.createProductUseCase = createProductUseCase;
        this.getProductsUseCase = getProductsUseCase;
        this.updateProductUseCase = updateProductUseCase;
        this.deleteProductUseCase = deleteProductUseCase;
    }

    @PostMapping
    public ResponseEntity<ProductResponse> createProduct(
            @Valid @RequestBody CreateProductRequest request) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(ProductResponse.from(
                        createProductUseCase.createProduct(request.name(), request.price())));
    }

    @GetMapping
    public List<ProductResponse> getProducts() {
        return getProductsUseCase.getProducts().stream()
                .map(ProductResponse::from)
                .toList();
    }

    @PutMapping("/{id}")
    public ProductResponse updateProduct(@PathVariable UUID id,
                                         @Valid @RequestBody UpdateProductRequest request) {
        return ProductResponse.from(
                updateProductUseCase.updateProduct(id, request.name(), request.price()));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(@PathVariable UUID id) {
        deleteProductUseCase.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
