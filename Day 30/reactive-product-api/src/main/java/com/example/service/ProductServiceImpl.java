package com.example.service;

import com.example.model.Product;
import com.example.repository.ProductRepository;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class ProductServiceImpl implements ProductService {

    private final ProductRepository repository;

    public ProductServiceImpl(ProductRepository repository) {
        this.repository = repository;
    }

    @Override
    public Flux<Product> getAllProducts() {
        return repository.findAll();
    }

    @Override
    public Mono<Product> getProductById(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
                ));
    }

    @Override
    public Mono<Product> createProduct(Product product) {
        // Let MySQL auto-generate ID
        return repository.save(product);
    }

    @Override
    public Mono<Product> updateProduct(Long id, Product updatedProduct) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
                ))
                .flatMap(existing -> {
                    existing.setName(updatedProduct.getName());
                    existing.setDescription(updatedProduct.getDescription());
                    existing.setPrice(updatedProduct.getPrice());
                    return repository.save(existing);
                });
    }

    @Override
    public Mono<Void> deleteProduct(Long id) {
        return repository.findById(id)
                .switchIfEmpty(Mono.error(
                    new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")
                ))
                .flatMap(repository::delete);
    }

    @Override
    public Flux<Product> getProductsByPriceRange(Double min, Double max) {
        return repository.findByPriceBetween(min, max);
    }
}
