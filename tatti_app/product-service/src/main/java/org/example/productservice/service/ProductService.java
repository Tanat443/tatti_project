package org.example.productservice.service;

import org.example.productservice.dto.ProductDTO;
import org.example.productservice.mapper.ProductMapper;
import org.example.productservice.model.Product;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.DateTimeException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ProductMapper productMapper;

    public Flux<ProductDTO> getAllProducts() {
        return productRepository.findAll()
                .map(productMapper::toDTO);
    }

    public Mono<ProductDTO> getProductById(Long id) {
        return productRepository.findById(id)
                .map(productMapper::toDTO)
                .switchIfEmpty(Mono.error(new ResponseStatusException(HttpStatus.NOT_FOUND, "Product not found")));
    }

    public Mono<ProductDTO> createProduct(ProductDTO dto) {
        return categoryRepository.findById(dto.getCategoryId())
                .switchIfEmpty(Mono.error(new RuntimeException("Category not found")))
                .flatMap(category -> {
                    Product product = productMapper.toEntity(dto);
                    product.setCategoryId(category.getId());
                    product.setCreatedAt(LocalDateTime.now()); // устанавливаем только createdAt
                    // не трогаем updatedAt

                    return productRepository.save(product)
                            .map(productMapper::toDTO);
                });
    }


    public Mono<ProductDTO> updateProduct(Long id, ProductDTO dto) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .flatMap(existing -> categoryRepository.findById(dto.getCategoryId())
                        .switchIfEmpty(Mono.error(new RuntimeException("Category not found")))
                        .flatMap(category -> {
                            productMapper.updateEntity(existing, dto);
                            existing.setCategoryId(category.getId());
                            existing.setUpdatedAt(LocalDateTime.now());

                            return productRepository.save(existing)
                                    .map(productMapper::toDTO);
                        }));
    }



    public Mono<Boolean> deleteProduct(Long id) {
        return productRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .flatMap(product -> productRepository.deleteById(product.getId())
                        .then(Mono.just(true)))
                .onErrorResume(e -> {
                    // Можно логировать ошибку
                    return Mono.just(false);
                });
    }


    public Flux<ProductDTO> getProductsByCategoryId(Long categoryId) {
        return categoryRepository.findById(categoryId)
                .switchIfEmpty(Mono.error(new RuntimeException("Category not found")))
                .flatMapMany(cat -> productRepository.findByCategoryId(categoryId)
                        .map(productMapper::toDTO));
    }

}
