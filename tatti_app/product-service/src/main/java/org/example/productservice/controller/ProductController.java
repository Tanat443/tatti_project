package org.example.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.productservice.dto.ProductDTO;
import org.example.productservice.dto.Status;
import org.example.productservice.model.Product;
import org.example.productservice.service.ProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/products")
public class ProductController {

    private final ProductService productService;

    @GetMapping
    public Flux<ProductDTO> getAllProducts() {
        return productService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Mono<ProductDTO> getProductById(@PathVariable UUID id) {
        return productService.getProductById(id);
    }

    @GetMapping("/category/{categoryId}")
    public Flux<ProductDTO> getProductsByCategory(@PathVariable UUID categoryId) {
        return productService.getProductsByCategoryId(categoryId);
    }

    @PostMapping
    public Mono<ProductDTO> createProduct(@RequestBody ProductDTO productDTO) {
        return productService.createProduct(productDTO);
    }

    @PutMapping("/{id}")
    public Mono<ProductDTO> updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productDTO) {
        return productService.updateProduct(id, productDTO);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Boolean>> deleteProduct(@PathVariable UUID id) {
        return productService.deleteProduct(id)
                .map(success -> success ? ResponseEntity.ok(true) : ResponseEntity.notFound().build());
    }

    //ONLY ADMIN / MODERATOR
    @PostMapping("/{id}/status")
    public Mono<ProductDTO> updateStatus (@PathVariable UUID id,
                                          @RequestBody Status status){
        return productService.updateStatus(id, status);
    }
}
