package com.example.service;

import com.example.model.Product;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface ProductService {

    Flux<Product> getAllProducts();

    Mono<Product> getProductById(Long id);

    Mono<Product> createProduct(Product product);

    Mono<Product> updateProduct(Long id, Product product);

    Mono<Void> deleteProduct(Long id);

    Flux<Product> getProductsByPriceRange(Double min, Double max);
}
