package org.example.productservice.repository;

import org.example.productservice.model.Product;
import org.example.productservice.model.ShopProduct;
import org.springframework.data.r2dbc.repository.R2dbcRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

import java.util.UUID;

@Repository
public interface ShopProductRepository extends R2dbcRepository<ShopProduct, UUID> {
    Flux<ShopProduct> getAllByShopId(UUID shopId);

}
