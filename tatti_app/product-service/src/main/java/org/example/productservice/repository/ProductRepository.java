    package org.example.productservice.repository;

    import org.example.productservice.model.Product;
    import org.springframework.data.repository.reactive.ReactiveCrudRepository;
    import org.springframework.stereotype.Repository;
    import reactor.core.publisher.Flux;

    import java.util.List;

    @Repository
    public interface ProductRepository extends ReactiveCrudRepository<Product, Long> {
        Flux<Product> findByCategoryId(Long categoryId);
    }
