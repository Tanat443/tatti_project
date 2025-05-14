package com.exapmle.userservice.service;


import com.exapmle.userservice.dto.ProductUniqueDTO;
import com.exapmle.userservice.dto.ShopProductDTO;
import com.exapmle.userservice.webClient.ShopProductClient;
import com.exapmle.userservice.model.ShopOwner;
import com.exapmle.userservice.repository.ShopOwnerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ShopOwnerService {

    private final ShopProductClient shopProductClient;
    private final ShopOwnerRepository shopOwnerRepository;

    public Flux<ShopProductDTO> getShopProductsByShopId(UUID shopId){

        return shopProductClient.getProductsByShopId(shopId);
    }

    public Mono<ShopProductDTO> getProductById(UUID id){
        return shopProductClient.getProductById(id);
    }

    public Mono<ShopProductDTO> addStandardProduct(ShopProductDTO shopProductDTO){
        shopProductDTO.setAddedAt(LocalDateTime.now());
        return shopProductClient.addStandardProduct(shopProductDTO);
    }

    public Mono<ProductUniqueDTO> addUniqueProduct(ProductUniqueDTO uniqueDTO){
        return shopProductClient.addUniqueProduct(uniqueDTO);
    }
    public Flux<ShopOwner> getAllShopOwners(){
        return shopOwnerRepository.findAll();
    }

    public Mono<ShopOwner> getOneShopOwner(UUID id){
        return shopOwnerRepository.findById(id);
    }

    public Mono<ShopOwner> getByUserId(UUID id){
        return shopOwnerRepository.findByUserId(id);
    }

    public Mono<ShopOwner> createShopOwner(ShopOwner shopOwner){
        return shopOwnerRepository.save(shopOwner);
    }

    public Mono<ShopOwner> updateShopOwner(ShopOwner shopOwner, UUID id){

        shopOwner.setId(id);

        return shopOwnerRepository.save(shopOwner);
    }

    public Mono<Boolean> deleteShopOwner(UUID id) {
        return shopOwnerRepository.existsById(id)
                .flatMap(exists -> {
                    if (exists) {
                        return shopOwnerRepository.deleteById(id).thenReturn(true);
                    } else {
                        return Mono.just(false);
                    }
                });
    }




}
