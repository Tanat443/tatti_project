package com.exapmle.userservice.controller;

import com.exapmle.userservice.dto.ProductUniqueDTO;
import com.exapmle.userservice.dto.ShopProductDTO;
import com.exapmle.userservice.model.ShopOwner;
import com.exapmle.userservice.service.ShopOwnerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping(value = "/api/shop-owners")
public class ShopController {

    private final ShopOwnerService shopOwnerService;
    @GetMapping("/products/{id}")
    public Flux<ShopProductDTO> getShopProductsByShopId(@PathVariable UUID id){
        return shopOwnerService.getShopProductsByShopId(id);
    }

    @PostMapping("/products/add-standard")
    public Mono <ShopProductDTO> addStandardProduct(@RequestBody ShopProductDTO standardProduct){
        return shopOwnerService.addStandardProduct(standardProduct);
    }

    @PostMapping("/products/add-unique")
    public Mono <ProductUniqueDTO> addUniqueProduct(@RequestBody ProductUniqueDTO uniqueDTO){
        return shopOwnerService.addUniqueProduct(uniqueDTO);
    }

    @GetMapping
    public Flux<ShopOwner> getAll() {
        return shopOwnerService.getAllShopOwners();
    }

    @GetMapping("/{id}")
    public Mono<ResponseEntity<ShopOwner>> getById(@PathVariable UUID id) {
        return shopOwnerService.getOneShopOwner(id)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @GetMapping("/user/{userId}")
    public Mono<ResponseEntity<ShopOwner>> getByUserId(@PathVariable UUID userId) {
        return shopOwnerService.getByUserId(userId)
                .map(ResponseEntity::ok)
                .defaultIfEmpty(ResponseEntity.notFound().build());
    }

    @PostMapping
    public Mono<ResponseEntity<ShopOwner>> create(@RequestBody ShopOwner shopOwner) {
        return shopOwnerService.createShopOwner(shopOwner)
                .map(saved -> ResponseEntity.ok().body(saved));
    }

    @PutMapping("/{id}")
    public Mono<ResponseEntity<ShopOwner>> update(@PathVariable UUID id, @RequestBody ShopOwner shopOwner) {
        return shopOwnerService.updateShopOwner(shopOwner, id)
                .map(ResponseEntity::ok);
    }

    @DeleteMapping("/{id}")
    public Mono<ResponseEntity<Void>> delete(@PathVariable UUID id) {
        return shopOwnerService.deleteShopOwner(id)
                .map(deleted -> deleted ? ResponseEntity.noContent().build() : ResponseEntity.notFound().build());
    }


}
