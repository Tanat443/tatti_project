package org.example.productservice.service;


import lombok.RequiredArgsConstructor;
import org.example.productservice.model.ShopProduct;
import org.example.productservice.repository.CategoryRepository;
import org.example.productservice.repository.ShopProductRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopProductService {

    private final ShopProductRepository shopProductRepository;
    private final CategoryRepository categoryRepository;

    public Flux<ShopProduct> getAllProducts() {
        return shopProductRepository.findAll();
    }

    public Mono<ShopProduct> getProductById(UUID id) {
        return shopProductRepository.findById(id);
    }

    public Mono<ShopProduct> createProduct(ShopProduct dto) {
        dto.setAddedAt(LocalDateTime.now());
        return shopProductRepository.save(dto);
    }

    public Flux<ShopProduct> getAllProductsByShopId(UUID shopId){
        return shopProductRepository.getAllByShopId(shopId);
    }

    public Mono<ShopProduct> updateProduct(UUID id, ShopProduct dto) {
        ShopProduct shopProduct = shopProductRepository.findById(id).block();
        if (shopProduct != null){
            dto.setId(id);
            return shopProductRepository.save(dto);
        }


        return null;
    }



    public Mono<Boolean> deleteProduct(UUID id) {
        return shopProductRepository.findById(id)
                .switchIfEmpty(Mono.error(new RuntimeException("Product not found")))
                .flatMap(product -> shopProductRepository.deleteById(product.getId())
                        .then(Mono.just(true)))
                .onErrorResume(e -> {
                    // Можно логировать ошибку
                    return Mono.just(false);
                });
    }

}
