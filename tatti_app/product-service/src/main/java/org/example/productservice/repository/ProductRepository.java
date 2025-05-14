    package org.example.productservice.repository;

    import org.example.productservice.model.Product;
    import org.springframework.data.r2dbc.repository.R2dbcRepository;
    import org.springframework.stereotype.Repository;
    import reactor.core.publisher.Flux;

    import java.util.UUID;

    @Repository
    public interface ProductRepository extends R2dbcRepository<Product, UUID> {
        Flux<Product> findByCategoryId(UUID categoryId);
    }
