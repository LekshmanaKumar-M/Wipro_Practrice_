package com.example.controller;

import com.example.model.Product;
import com.example.service.ProductService;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/products")
public class ProductController {

    private final ProductService service;

    public ProductController(ProductService service) {
        this.service = service;
    }

    // 1. Get all
    @GetMapping
    public Flux<Product> getAll() {
        return service.getAllProducts();
    }

    // 2. Get by ID
    @GetMapping("/{id}")
    public Mono<Product> getById(@PathVariable Long id) {
        return service.getProductById(id);
    }

    // 3. Create product
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Product> create(@RequestBody Product product) {
        return service.createProduct(product);
    }

    // 4. Update product
    @PutMapping("/{id}")
    public Mono<Product> update(@PathVariable Long id, @RequestBody Product product) {
        return service.updateProduct(id, product);
    }

    // 5. Delete product
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public Mono<Void> delete(@PathVariable Long id) {
        return service.deleteProduct(id);
    }

    // 6. Price range query
    @GetMapping("/price-range")
    public Flux<Product> getByRange(@RequestParam Double min, @RequestParam Double max) {
        return service.getProductsByPriceRange(min, max);
    }
}
