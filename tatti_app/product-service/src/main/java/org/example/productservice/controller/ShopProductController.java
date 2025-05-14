package org.example.productservice.controller;

import lombok.RequiredArgsConstructor;
import org.example.productservice.dto.ProductDTO;
import org.example.productservice.model.Product;
import org.example.productservice.model.ShopProduct;
import org.example.productservice.service.ProductService;
import org.example.productservice.service.ShopProductService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/shop-products")
public class ShopProductController {

    private final ShopProductService shopProductService;
    private final ProductService productService;

    @GetMapping
    public Flux<ShopProduct> getAllProducts() {
        return shopProductService.getAllProducts();
    }

    @GetMapping("/{id}")
    public Mono<ShopProduct> getProductById(@PathVariable UUID id) {
        return shopProductService.getProductById(id);
    }

    @GetMapping("/shop/{id}")
    public Flux<ShopProduct> getAllProductsByShopId(@PathVariable UUID id){
       return shopProductService.getAllProductsByShopId(id);
    }

    @PostMapping("/")
    public Mono<ShopProduct> createProduct(@RequestBody ShopProduct shopProduct) {
        return shopProductService.createProduct(shopProduct);
    }

    @PostMapping("/unique")
    public Mono<ProductDTO> createUniqueProduct(@RequestBody ProductDTO product) {
        return productService.createProduct(product);
    }

    @PutMapping("/{id}")
    public Mono<ShopProduct> updateProduct(@PathVariable UUID id, @RequestBody ShopProduct shopProduct) {
        return shopProductService.updateProduct(id, shopProduct);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Boolean>> deleteProduct(@PathVariable UUID id) {
        return shopProductService.deleteProduct(id)
                .map(success -> success ? ResponseEntity.ok(true) : ResponseEntity.notFound().build());
    }

}
